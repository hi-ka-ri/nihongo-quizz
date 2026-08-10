import { useState, useEffect } from 'react';
import { unitApi } from '../services/api';
import type { Unit } from '../types';
import { Link } from 'react-router-dom';

export const Units = () => {
  const [units, setUnits] = useState<Unit[]>([]);

  useEffect(() => {
    unitApi.getUnits().then(setUnits).catch(() => {});
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Learning Units</h1>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {units.map(unit => (
          <div key={unit.id} className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden flex flex-col group hover:border-primary transition-colors">
            <div className="h-40 bg-white border-b border-gray-100 flex items-center justify-center p-4">
              {unit.imageUrl ? (
                <img src={unit.imageUrl} alt={unit.title} className="max-h-full max-w-full object-contain group-hover:scale-105 transition-transform duration-300" />
              ) : (
                <div className="w-full h-full bg-gray-100 rounded-lg" />
              )}
            </div>
            <div className="p-6 flex flex-col flex-1">
              <div className="flex justify-between items-start mb-2">
                <span className="inline-block px-2 py-1 bg-gray-100 text-xs font-semibold text-gray-600 rounded">
                  {unit.level}
                </span>
                <span className="text-xs font-medium text-gray-500">{unit.vocabCount} words</span>
              </div>
              <h3 className="text-xl font-bold text-gray-900 mb-2 group-hover:text-primary transition-colors">
                {unit.title}
              </h3>
              <p className="text-sm text-gray-600 mb-6 flex-1">
                {unit.description}
              </p>
              

              <div className="flex gap-2 mt-auto">
                <Link to={`/units/${unit.id}`} className="flex-1 text-center py-2.5 bg-primary hover:bg-primary-dark text-white rounded-lg font-medium text-sm transition-colors shadow-sm">
                  Xem chi tiết
                </Link>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
