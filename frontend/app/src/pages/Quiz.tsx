import { useState, useEffect } from 'react';
import { useParams, useNavigate, useSearchParams } from 'react-router-dom';
import { quizApi } from '../services/api';
import type { Question } from '../types';
import { X, ChevronLeft, ChevronRight, Check } from 'lucide-react';

export const Quiz = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [questions, setQuestions] = useState<Question[]>([]);
  const [shuffledQuestions, setShuffledQuestions] = useState<Question[]>([]);
  const [optionsMap, setOptionsMap] = useState<Record<number, {id:string, text:string}[]>>({});
  const [currentIndex, setCurrentIndex] = useState(0);
  const [answers, setAnswers] = useState<Record<number, string>>({});
  const [loading, setLoading] = useState(true);

  const initializeQuiz = (qs: Question[], isRandom: boolean) => {
    const newQs = isRandom ? [...qs].sort(() => Math.random() - 0.5) : [...qs];
    const newOpts: Record<number, any> = {};
    newQs.forEach(q => {
      newOpts[q.id] = [
        { id: 'A', text: q.optionA },
        { id: 'B', text: q.optionB },
        { id: 'C', text: q.optionC },
        { id: 'D', text: q.optionD }
      ].sort(() => Math.random() - 0.5);
    });
    setShuffledQuestions(newQs);
    setOptionsMap(newOpts);
    setCurrentIndex(0);
  };

  useEffect(() => {
    if (id) {
      quizApi.getQuestions(Number(id)).then(data => {
        let processedData = [...data];
        const isRandom = searchParams.get('random') === 'true';
        if (isRandom) {
          processedData.sort(() => Math.random() - 0.5);
        }
        const limitStr = searchParams.get('limit');
        if (limitStr) {
          processedData = processedData.slice(0, Number(limitStr));
        }

        setQuestions(processedData);
        initializeQuiz(processedData, false);
        setLoading(false);
      }).catch(() => setLoading(false));
    }
  }, [id, searchParams]);

  if (loading) return <div className="flex h-screen items-center justify-center">Loading quiz...</div>;
  if (!shuffledQuestions.length) return <div className="text-center py-20">No questions available for this unit.</div>;

  const currentQuestion = shuffledQuestions[currentIndex];

  const handleSelectOption = (option: string) => {
    if (answers[currentQuestion.id]) return; // prevent changing answer
    setAnswers(prev => ({ ...prev, [currentQuestion.id]: option }));
  };

  const handleNext = () => {
    if (currentIndex < shuffledQuestions.length - 1) setCurrentIndex(prev => prev + 1);
  };

  const handlePrev = () => {
    if (currentIndex > 0) setCurrentIndex(prev => prev - 1);
  };

  const handleSubmit = async () => {
    try {
      setLoading(true);
      const attempt = await quizApi.startAttempt(Number(id));
      
      let score = 0;
      for (const question of questions) {
        const selected = answers[question.id];
        if (selected) {
          await quizApi.submitAnswer(attempt.id, question.id, selected);
          if (selected === question.correctAnswer) score++;
        }
      }

      await quizApi.submitQuiz(attempt.id);
      navigate(`/quiz/${attempt.id}/result`, { state: { attempt: { ...attempt, score, totalQuestions: shuffledQuestions.length }, questions: shuffledQuestions, answers } });
    } catch (e) {
      alert('Error submitting quiz');
      setLoading(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto flex gap-8">
      {/* Navigator */}
      <div className="w-64 shrink-0 bg-white rounded-xl shadow-sm border border-gray-100 p-6 hidden lg:block h-fit">
        <h3 className="font-bold text-gray-900 mb-4">Question Navigator</h3>
        <div className="grid grid-cols-5 gap-2">
          {shuffledQuestions.map((q, idx) => {
            const isAnswered = answers[q.id] !== undefined;
            const isCurrent = idx === currentIndex;
            return (
              <button
                key={q.id}
                onClick={() => setCurrentIndex(idx)}
                className={`w-10 h-10 rounded-lg flex items-center justify-center font-medium text-sm transition-all ${
                  isCurrent ? 'ring-2 ring-primary ring-offset-2' : ''
                } ${
                  isAnswered 
                    ? 'bg-primary text-white' 
                    : 'bg-gray-100 text-gray-500 hover:bg-gray-200'
                }`}
              >
                {idx + 1}
              </button>
            );
          })}
        </div>
      </div>

      {/* Main Content */}
      <div className="flex-1">
        <div className="flex items-center justify-between mb-8">
          <button onClick={() => navigate(-1)} className="p-2 hover:bg-gray-100 rounded-full transition-colors">
            <X className="w-6 h-6 text-gray-500" />
          </button>
          
          <div className="text-gray-500 font-medium bg-white px-4 py-1.5 rounded-full shadow-sm border border-gray-100">
            {currentIndex + 1} / {shuffledQuestions.length}
          </div>
          
          <button onClick={() => initializeQuiz(questions, true)} className="text-sm font-medium bg-gray-100 px-3 py-1.5 rounded-full text-gray-600 hover:bg-gray-200 transition">
            Đảo câu hỏi
          </button>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-8 mb-6">
          <h2 className="text-xl font-medium text-gray-900 mb-6">{currentQuestion.questionText}</h2>
          <div className="space-y-3">
            {(optionsMap[currentQuestion.id] || []).map((option) => (
              <button
                key={option.id}
                onClick={() => handleSelectOption(option.id)}
                disabled={!!answers[currentQuestion.id]}
                className={`w-full text-left p-4 rounded-xl border-2 transition-all flex justify-between items-center
                  ${answers[currentQuestion.id] === option.id 
                    ? (option.id === currentQuestion.correctAnswer ? 'border-green-500 bg-green-50 text-green-700 font-bold' : 'border-red-500 bg-red-50 text-red-700 font-bold')
                    : (answers[currentQuestion.id] && option.id === currentQuestion.correctAnswer ? 'border-green-500 bg-green-50 text-green-700 font-bold' : 'border-gray-100 hover:border-gray-200 hover:bg-gray-50 text-gray-700')
                  }
                `}
              >
                <span><span className="font-bold mr-3">{option.id}.</span> {option.text}</span>
                {answers[currentQuestion.id] === option.id && (
                  option.id === currentQuestion.correctAnswer ? <Check className="w-5 h-5 text-green-600" /> : <X className="w-5 h-5 text-red-600" />
                )}
                {answers[currentQuestion.id] && answers[currentQuestion.id] !== option.id && option.id === currentQuestion.correctAnswer && (
                  <Check className="w-5 h-5 text-green-600" />
                )}
              </button>
            ))}
          </div>
        </div>

        {answers[currentQuestion.id] && (
          <div className={`p-6 rounded-xl border ${answers[currentQuestion.id] === currentQuestion.correctAnswer ? 'bg-green-50 border-green-200' : 'bg-red-50 border-red-200'} mb-6 animate-in slide-in-from-bottom-2 duration-300`}>
            <h3 className={`text-lg font-bold mb-3 ${answers[currentQuestion.id] === currentQuestion.correctAnswer ? 'text-green-700' : 'text-red-700'}`}>
              {answers[currentQuestion.id] === currentQuestion.correctAnswer ? '🎉 Chính xác!' : '❌ Sai rồi!'}
            </h3>
            
            <div className="bg-white p-5 rounded-lg shadow-sm border border-gray-100/50">
              <div className="flex items-end gap-3 mb-2">
                <span className="text-3xl font-bold text-gray-900">{currentQuestion.vocabulary?.kanji || currentQuestion.vocabulary?.hiragana}</span>
                {currentQuestion.vocabulary?.kanji && (
                  <span className="text-xl text-gray-500 font-medium">{currentQuestion.vocabulary.hiragana}</span>
                )}
                {currentQuestion.vocabulary?.sinoVietnamese && (
                  <span className="text-sm font-bold text-blue-700 bg-blue-100/80 px-2.5 py-1 rounded-md ml-2">
                    {currentQuestion.vocabulary.sinoVietnamese}
                  </span>
                )}
              </div>
              <p className="text-gray-800 text-lg mt-3"><span className="font-semibold text-gray-500 text-sm uppercase tracking-wider block mb-1">Ý nghĩa</span> {currentQuestion.vocabulary?.meaning}</p>
              {currentQuestion.vocabulary?.exampleSentence && (
                <p className="text-gray-600 mt-4 text-sm bg-gray-50 p-3 rounded-lg italic">
                  <span className="font-semibold not-italic block mb-1 text-gray-700">Ví dụ:</span>
                  {currentQuestion.vocabulary.exampleSentence}
                </p>
              )}
            </div>
          </div>
        )}

        <div className="flex items-center justify-between mt-8 pt-6 border-t border-gray-100">
          <button 
            onClick={handlePrev} 
            disabled={currentIndex === 0}
            className="flex items-center gap-2 px-6 py-3 rounded-xl font-medium text-gray-700 bg-white border border-gray-200 hover:bg-gray-50 disabled:opacity-50 transition-colors"
          >
            <ChevronLeft className="w-5 h-5" /> Previous
          </button>

          {currentIndex === shuffledQuestions.length - 1 ? (
            <button 
              onClick={handleSubmit}
              className="flex items-center gap-2 px-8 py-3 rounded-xl font-medium text-white bg-primary hover:bg-primary-dark transition-colors shadow-sm"
            >
              Nộp bài
            </button>
          ) : (
            <button 
              onClick={handleNext}
              className="flex items-center gap-2 px-6 py-3 rounded-xl font-medium text-gray-700 bg-white border border-gray-200 hover:bg-gray-50 transition-colors"
            >
              Next <ChevronRight className="w-5 h-5" />
            </button>
          )}
        </div>
      </div>
    </div>
  );
};
