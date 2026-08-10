import { useState, useEffect } from 'react';
import { adminApi, unitApi, quizApi } from '../../services/api';
import type { Unit, Question } from '../../types';
import { Pencil, Trash2, Plus } from 'lucide-react';

export const AdminQuestions = () => {
  const [units, setUnits] = useState<Unit[]>([]);
  const [selectedUnitId, setSelectedUnitId] = useState<number | null>(null);
  const [questions, setQuestions] = useState<Question[]>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [currentQuestion, setCurrentQuestion] = useState<Partial<Question>>({
    questionType: 'MULTIPLE_CHOICE',
    correctAnswer: 'A'
  });

  useEffect(() => {
    unitApi.getUnits().then(data => {
      setUnits(data);
      if (data.length > 0) {
        setSelectedUnitId(data[0].id);
      }
    }).catch(() => {});
  }, []);

  const loadQuestions = () => {
    if (selectedUnitId) {
      quizApi.getQuestions(selectedUnitId).then(setQuestions).catch(() => {});
    }
  };

  useEffect(() => {
    loadQuestions();
  }, [selectedUnitId]);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedUnitId) return;
    
    try {
      if (currentQuestion.id) {
        await adminApi.updateQuestion(currentQuestion.id, currentQuestion);
      } else {
        // Needs a vocabId, hardcoding for now as 1 or requesting it from form in real prod app.
        // Let's assume we fetch a list of vocabs to pick from. But for simple MVP, we just assign to the first vocab in unit or use a placeholder.
        await adminApi.createQuestion(selectedUnitId, 1, currentQuestion); 
      }
      setIsEditing(false);
      setCurrentQuestion({ questionType: 'MULTIPLE_CHOICE', correctAnswer: 'A' });
      loadQuestions();
    } catch (e) {
      alert('Error saving question');
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this question?')) {
      try {
        await adminApi.deleteQuestion(id);
        loadQuestions();
      } catch (e) {
        alert('Error deleting question');
      }
    }
  };

  const handleShuffle = () => {
    setQuestions(prev => [...prev].sort(() => Math.random() - 0.5));
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-900">Manage Questions</h1>
        <div className="flex gap-2">
          <button
            onClick={handleShuffle}
            disabled={!selectedUnitId || questions.length === 0}
            className="flex items-center gap-2 bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition disabled:opacity-50 font-medium text-sm"
          >
            Đảo ngẫu nhiên
          </button>
          <button
            onClick={() => { setCurrentQuestion({ questionType: 'MULTIPLE_CHOICE', correctAnswer: 'A' }); setIsEditing(true); }}
            disabled={!selectedUnitId}
            className="flex items-center gap-2 bg-primary text-white px-4 py-2 rounded-lg hover:bg-primary-dark transition disabled:opacity-50 font-medium text-sm"
          >
            <Plus className="w-4 h-4" /> Add Question
          </button>
        </div>
      </div>

      <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex flex-col sm:flex-row sm:items-center gap-4">
        <label className="font-medium text-gray-700 whitespace-nowrap">Select Unit:</label>
        <select 
          className="w-full sm:w-64 border border-gray-200 rounded-lg p-2.5 bg-gray-50 text-gray-900 focus:ring-2 focus:ring-primary focus:border-primary transition-all outline-none"
          value={selectedUnitId || ''}
          onChange={(e) => setSelectedUnitId(Number(e.target.value))}
        >
          {units.map(u => <option key={u.id} value={u.id}>{u.title}</option>)}
        </select>
      </div>

      {isEditing && (
        <form onSubmit={handleSave} className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 grid grid-cols-2 gap-4">
          <div className="col-span-2"><h2 className="text-lg font-bold">{currentQuestion.id ? 'Edit' : 'New'} Question</h2></div>
          
          <div className="col-span-2"><label className="block text-sm">Question Text *</label><input required value={currentQuestion.questionText || ''} onChange={e => setCurrentQuestion({...currentQuestion, questionText: e.target.value})} className="w-full border p-2 rounded" /></div>
          
          <div><label className="block text-sm">Option A *</label><input required value={currentQuestion.optionA || ''} onChange={e => setCurrentQuestion({...currentQuestion, optionA: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Option B *</label><input required value={currentQuestion.optionB || ''} onChange={e => setCurrentQuestion({...currentQuestion, optionB: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Option C *</label><input required value={currentQuestion.optionC || ''} onChange={e => setCurrentQuestion({...currentQuestion, optionC: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Option D *</label><input required value={currentQuestion.optionD || ''} onChange={e => setCurrentQuestion({...currentQuestion, optionD: e.target.value})} className="w-full border p-2 rounded" /></div>
          
          <div>
            <label className="block text-sm font-medium mb-1">Correct Answer *</label>
            <select required value={currentQuestion.correctAnswer || 'A'} onChange={e => setCurrentQuestion({...currentQuestion, correctAnswer: e.target.value})} className="w-full border border-gray-300 p-2 rounded-lg bg-white outline-none focus:ring-2 focus:ring-primary focus:border-primary">
              <option value="A">A</option>
              <option value="B">B</option>
              <option value="C">C</option>
              <option value="D">D</option>
            </select>
          </div>
          
          <div className="col-span-2 flex gap-2 mt-4">
            <button type="submit" className="bg-primary text-white px-4 py-2 rounded">Save</button>
            <button type="button" onClick={() => setIsEditing(false)} className="bg-gray-200 px-4 py-2 rounded">Cancel</button>
          </div>
        </form>
      )}

      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left">
          <thead className="bg-gray-50 border-b border-gray-100">
            <tr>
              <th className="p-4 font-medium text-gray-500">ID</th>
              <th className="p-4 font-medium text-gray-500 w-1/2">Question</th>
              <th className="p-4 font-medium text-gray-500">Answer</th>
              <th className="p-4 font-medium text-gray-500 text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {questions.map(q => (
              <tr key={q.id} className="hover:bg-gray-50">
                <td className="p-4">{q.id}</td>
                <td className="p-4 font-medium">{q.questionText}</td>
                <td className="p-4 text-green-600 font-bold">{q.correctAnswer}</td>
                <td className="p-4 text-right flex justify-end gap-2">
                  <button onClick={() => { setCurrentQuestion(q); setIsEditing(true); }} className="p-2 text-blue-600 hover:bg-blue-50 rounded">
                    <Pencil className="w-4 h-4" />
                  </button>
                  <button onClick={() => handleDelete(q.id)} className="p-2 text-red-600 hover:bg-red-50 rounded">
                    <Trash2 className="w-4 h-4" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
