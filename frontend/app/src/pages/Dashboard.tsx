import { useState, useEffect } from 'react';
import { unitApi } from '../services/api';
import type { Unit } from '../types';
import { useAuth } from '../store/AuthContext';
import { Link } from 'react-router-dom';

export const Dashboard = () => {
  const [recentUnits, setRecentUnits] = useState<Unit[]>([]);

  const { user } = useAuth();

  useEffect(() => {
    unitApi.getUnits().then(units => setRecentUnits(units.slice(0, 2))).catch(() => {});
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">
          {user ? `Welcome back, ${user.username}!` : 'Welcome to Nihongo!'}
        </h1>
      </div>

      <h2 className="text-lg font-bold text-gray-900 mt-8 mb-4">Start Learning</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {recentUnits.map(unit => (
          <Link to={`/units/${unit.id}`} key={unit.id} className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 hover:border-primary transition-colors group">
            <div className="flex justify-between items-start mb-4">
              <div>
                <span className="inline-block px-2 py-1 bg-gray-100 text-xs font-semibold text-gray-600 rounded mb-2">
                  {unit.level || 'N3'}
                </span>
                <h3 className="text-xl font-bold text-gray-900 group-hover:text-primary transition-colors">
                  {unit.title}
                </h3>
              </div>
            </div>
            <div className="text-sm text-gray-500 mt-4">
              <span>{unit.vocabCount} words</span>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
};
