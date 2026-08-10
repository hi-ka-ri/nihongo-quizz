import { useState, useEffect } from 'react';
import { adminApi, unitApi, vocabApi } from '../../services/api';
import type { Unit, Vocabulary } from '../../types';
import { Pencil, Trash2, Plus } from 'lucide-react';

export const AdminVocabularies = () => {
  const [units, setUnits] = useState<Unit[]>([]);
  const [selectedUnitId, setSelectedUnitId] = useState<number | null>(null);
  const [vocabularies, setVocabularies] = useState<Vocabulary[]>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [currentVocab, setCurrentVocab] = useState<Partial<Vocabulary>>({});

  useEffect(() => {
    unitApi.getUnits().then(data => {
      setUnits(data);
      if (data.length > 0) {
        setSelectedUnitId(data[0].id);
      }
    }).catch(() => {});
  }, []);

  const loadVocabularies = () => {
    if (selectedUnitId) {
      vocabApi.getVocabByUnit(selectedUnitId).then(setVocabularies).catch(() => {});
    }
  };

  useEffect(() => {
    loadVocabularies();
  }, [selectedUnitId]);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedUnitId) return;
    
    try {
      if (currentVocab.id) {
        await adminApi.updateVocabulary(currentVocab.id, currentVocab);
      } else {
        await adminApi.createVocabulary(selectedUnitId, currentVocab);
      }
      setIsEditing(false);
      setCurrentVocab({});
      loadVocabularies();
    } catch (e) {
      alert('Error saving vocabulary');
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this vocabulary?')) {
      try {
        await adminApi.deleteVocabulary(id);
        loadVocabularies();
      } catch (e) {
        alert('Error deleting vocabulary');
      }
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-900">Manage Vocabularies</h1>
        <button
          onClick={() => { setCurrentVocab({}); setIsEditing(true); }}
          disabled={!selectedUnitId}
          className="flex items-center gap-2 bg-primary text-white px-4 py-2 rounded-lg hover:bg-primary-dark transition disabled:opacity-50"
        >
          <Plus className="w-4 h-4" /> Add Vocabulary
        </button>
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
          <div className="col-span-2"><h2 className="text-lg font-bold">{currentVocab.id ? 'Edit' : 'New'} Vocabulary</h2></div>
          
          <div><label className="block text-sm">Kanji</label><input value={currentVocab.kanji || ''} onChange={e => setCurrentVocab({...currentVocab, kanji: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Hiragana *</label><input required value={currentVocab.hiragana || ''} onChange={e => setCurrentVocab({...currentVocab, hiragana: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Romaji *</label><input required value={currentVocab.romaji || ''} onChange={e => setCurrentVocab({...currentVocab, romaji: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Meaning *</label><input required value={currentVocab.meaning || ''} onChange={e => setCurrentVocab({...currentVocab, meaning: e.target.value})} className="w-full border p-2 rounded" /></div>
          <div><label className="block text-sm">Âm Hán (Sino-Vietnamese)</label><input value={currentVocab.sinoVietnamese || ''} onChange={e => setCurrentVocab({...currentVocab, sinoVietnamese: e.target.value})} className="w-full border p-2 rounded" /></div>
          
          <div className="col-span-2"><label className="block text-sm">Example Sentence</label><input value={currentVocab.exampleSentence || ''} onChange={e => setCurrentVocab({...currentVocab, exampleSentence: e.target.value})} className="w-full border p-2 rounded" /></div>
          
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
              <th className="p-4 font-medium text-gray-500 w-16">STT</th>
              <th className="p-4 font-medium text-gray-500">Kanji / Kana</th>
              <th className="p-4 font-medium text-gray-500">Âm Hán</th>
              <th className="p-4 font-medium text-gray-500">Romaji</th>
              <th className="p-4 font-medium text-gray-500">Meaning</th>
              <th className="p-4 font-medium text-gray-500 text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {vocabularies.map((v, index) => (
              <tr key={v.id} className="hover:bg-gray-50">
                <td className="p-4 text-gray-500">{index + 1}</td>
                <td className="p-4 font-medium">{v.kanji ? `${v.kanji} (${v.hiragana})` : v.hiragana}</td>
                <td className="p-4">{v.sinoVietnamese || '-'}</td>
                <td className="p-4">{v.romaji}</td>
                <td className="p-4">{v.meaning}</td>
                <td className="p-4 text-right flex justify-end gap-2">
                  <button onClick={() => { setCurrentVocab(v); setIsEditing(true); }} className="p-2 text-blue-600 hover:bg-blue-50 rounded">
                    <Pencil className="w-4 h-4" />
                  </button>
                  <button onClick={() => handleDelete(v.id)} className="p-2 text-red-600 hover:bg-red-50 rounded">
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
