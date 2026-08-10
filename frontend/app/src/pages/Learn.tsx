import { useState, useEffect } from 'react';
import { useParams, useNavigate, useSearchParams } from 'react-router-dom';
import { vocabApi } from '../services/api';
import type { Vocabulary } from '../types';
import { ArrowLeft, Volume2, CheckCircle, XCircle, ChevronLeft, ChevronRight } from 'lucide-react';

export const Learn = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [vocabularies, setVocabularies] = useState<Vocabulary[]>([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isFlipped, setIsFlipped] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (id) {
      vocabApi.getVocabByUnit(Number(id)).then(data => {
        let processedData = [...data];
        if (searchParams.get('random') === 'true') {
          processedData.sort(() => Math.random() - 0.5);
        }
        const limitStr = searchParams.get('limit');
        if (limitStr) {
          processedData = processedData.slice(0, Number(limitStr));
        }
        setVocabularies(processedData);
        setLoading(false);
      });
    }
  }, [id, searchParams]);

  const currentVocab = vocabularies[currentIndex];

  const handleNext = () => {
    if (currentIndex < vocabularies.length - 1) {
      setCurrentIndex(prev => prev + 1);
      setIsFlipped(false);
    }
  };

  const handlePrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex(prev => prev - 1);
      setIsFlipped(false);
    }
  };

  const playAudio = (e: React.MouseEvent) => {
    e.stopPropagation();
    if (currentVocab && 'speechSynthesis' in window) {
      const utterance = new SpeechSynthesisUtterance(currentVocab.kanji || currentVocab.hiragana);
      utterance.lang = 'ja-JP';
      window.speechSynthesis.speak(utterance);
    }
  };

  if (loading) return <div className="text-center py-20">Loading flashcards...</div>;
  if (!vocabularies.length) return <div className="text-center py-20">No vocabulary found for this unit.</div>;

  const progress = ((currentIndex + 1) / vocabularies.length) * 100;

  return (
    <div className="max-w-3xl mx-auto flex flex-col h-[calc(100vh-120px)]">
      <div className="flex items-center justify-between mb-6">
        <button onClick={() => navigate(-1)} className="text-gray-500 hover:text-gray-900 flex items-center gap-2">
          <ArrowLeft size={20} /> Back
        </button>
        <div className="text-sm font-bold text-gray-500">
          {currentIndex + 1} / {vocabularies.length}
        </div>
      </div>

      <div className="w-full bg-gray-200 rounded-full h-2 mb-8">
        <div className="bg-primary h-2 rounded-full transition-all duration-300" style={{ width: `${progress}%` }}></div>
      </div>

      {/* Flashcard Container */}
      <div 
        className="flex-1 relative perspective-1000 cursor-pointer mb-8"
        onClick={() => setIsFlipped(!isFlipped)}
      >
        <div className={`w-full h-full min-h-[400px] transition-transform duration-500 transform-style-preserve-3d relative ${isFlipped ? 'rotate-y-180' : ''}`}>
          
          {/* Front */}
          <div className="absolute w-full h-full bg-white rounded-3xl shadow-md border border-gray-100 backface-hidden flex flex-col items-center justify-center p-8">
            <h2 className="text-8xl font-black text-gray-900 mb-6">{currentVocab.kanji || currentVocab.hiragana}</h2>
            <p className="text-gray-400 text-sm">Click anywhere to flip</p>
          </div>

          {/* Back */}
          <div className="absolute w-full h-full bg-white rounded-3xl shadow-md border-2 border-primary backface-hidden rotate-y-180 flex flex-col items-center justify-center p-8 text-center">
            <button onClick={playAudio} className="absolute top-6 right-6 p-3 bg-gray-50 hover:bg-red-50 text-gray-600 hover:text-primary rounded-full transition-colors">
              <Volume2 size={24} />
            </button>
            
            {currentVocab.sinoVietnamese && (
              <span className="text-xl md:text-2xl font-bold text-blue-700 bg-blue-100/80 px-4 py-2 rounded-xl mb-6 inline-block tracking-wider uppercase">
                {currentVocab.sinoVietnamese}
              </span>
            )}
            
            <h2 className="text-7xl md:text-8xl font-black text-primary mb-8 leading-tight">{currentVocab.hiragana}</h2>
            
            <div className="w-2/3 h-px bg-gray-100 mb-8"></div>
            
            <h4 className="text-3xl md:text-4xl font-bold text-gray-800">{currentVocab.meaning}</h4>
          </div>
        </div>
      </div>

      {/* Controls */}
      <div className="flex items-center justify-between gap-4 mt-auto">
        <button 
          onClick={handlePrev}
          disabled={currentIndex === 0}
          className="p-4 rounded-full bg-white shadow-sm border border-gray-200 text-gray-600 hover:text-primary disabled:opacity-50 disabled:hover:text-gray-600 transition-colors"
        >
          <ChevronLeft size={24} />
        </button>

        <div className="flex gap-4 flex-1 justify-center">
          <button 
            onClick={() => { handleNext(); }}
            className="flex-1 max-w-[200px] flex items-center justify-center gap-2 py-4 bg-white hover:bg-red-50 text-red-500 border border-red-200 rounded-xl font-bold transition-colors"
          >
            <XCircle size={20} /> Chưa nhớ
          </button>
          <button 
            onClick={() => { handleNext(); }}
            className="flex-1 max-w-[200px] flex items-center justify-center gap-2 py-4 bg-primary hover:bg-primary-dark text-white rounded-xl font-bold transition-colors shadow-md shadow-red-200"
          >
            <CheckCircle size={20} /> Đã nhớ
          </button>
        </div>

        <button 
          onClick={handleNext}
          disabled={currentIndex === vocabularies.length - 1}
          className="p-4 rounded-full bg-white shadow-sm border border-gray-200 text-gray-600 hover:text-primary disabled:opacity-50 disabled:hover:text-gray-600 transition-colors"
        >
          <ChevronRight size={24} />
        </button>
      </div>
    </div>
  );
};
