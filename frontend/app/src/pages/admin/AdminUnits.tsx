import { useState, useEffect } from 'react';
import { adminApi, unitApi } from '../../services/api';
import type { Unit } from '../../types';
import { Pencil, Trash2, Plus } from 'lucide-react';

export const AdminUnits = () => {
  const [units, setUnits] = useState<Unit[]>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [currentUnit, setCurrentUnit] = useState<Partial<Unit>>({});

  const loadUnits = () => {
    unitApi.getUnits().then(setUnits).catch(() => {});
  };

  useEffect(() => {
    loadUnits();
  }, []);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (currentUnit.id) {
        await adminApi.updateUnit(currentUnit.id, currentUnit);
      } else {
        await adminApi.createUnit(currentUnit);
      }
      setIsEditing(false);
      setCurrentUnit({});
      loadUnits();
    } catch (e) {
      alert('Error saving unit');
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Are you sure you want to delete this unit?')) {
      try {
        await adminApi.deleteUnit(id);
        loadUnits();
      } catch (e) {
        alert('Error deleting unit');
      }
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-900">Manage Units</h1>
        <button
          onClick={() => { setCurrentUnit({}); setIsEditing(true); }}
          className="flex items-center gap-2 bg-primary text-white px-4 py-2 rounded-lg hover:bg-primary-dark transition"
        >
          <Plus className="w-4 h-4" /> Add Unit
        </button>
      </div>

      {isEditing && (
        <form onSubmit={handleSave} className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 space-y-4">
          <h2 className="text-lg font-bold">{currentUnit.id ? 'Edit Unit' : 'New Unit'}</h2>
          <div>
            <label className="block text-sm font-medium mb-1">Title</label>
            <input required value={currentUnit.title || ''} onChange={e => setCurrentUnit({...currentUnit, title: e.target.value})} className="w-full border p-2 rounded" />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">Description</label>
            <textarea required value={currentUnit.description || ''} onChange={e => setCurrentUnit({...currentUnit, description: e.target.value})} className="w-full border p-2 rounded" />
          </div>
          <div className="flex gap-2">
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
              <th className="p-4 font-medium text-gray-500">Title</th>
              <th className="p-4 font-medium text-gray-500">Description</th>
              <th className="p-4 font-medium text-gray-500 text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {units.map(unit => (
              <tr key={unit.id} className="hover:bg-gray-50">
                <td className="p-4">{unit.id}</td>
                <td className="p-4 font-medium">{unit.title}</td>
                <td className="p-4 text-gray-500 truncate max-w-xs">{unit.description}</td>
                <td className="p-4 text-right flex justify-end gap-2">
                  <button onClick={() => { setCurrentUnit(unit); setIsEditing(true); }} className="p-2 text-blue-600 hover:bg-blue-50 rounded">
                    <Pencil className="w-4 h-4" />
                  </button>
                  <button onClick={() => handleDelete(unit.id)} className="p-2 text-red-600 hover:bg-red-50 rounded">
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
