import { useState, useEffect } from 'react';
import { adminApi } from '../../services/api';
import { Users, BookOpen, FileText, HelpCircle, Trophy } from 'lucide-react';

export const AdminDashboard = () => {
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalUnits: 0,
    totalVocabularies: 0,
    totalQuestions: 0,
    totalQuizAttempts: 0
  });

  useEffect(() => {
    adminApi.getDashboardStats().then(setStats).catch(() => {});
  }, []);

  const statCards = [
    { label: 'Total Users', value: stats.totalUsers, icon: Users, color: 'bg-blue-500' },
    { label: 'Total Units', value: stats.totalUnits, icon: BookOpen, color: 'bg-green-500' },
    { label: 'Vocabularies', value: stats.totalVocabularies, icon: FileText, color: 'bg-purple-500' },
    { label: 'Questions', value: stats.totalQuestions, icon: HelpCircle, color: 'bg-yellow-500' },
    { label: 'Quiz Attempts', value: stats.totalQuizAttempts, icon: Trophy, color: 'bg-red-500' }
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Admin Dashboard</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-6">
        {statCards.map((stat, i) => {
          const Icon = stat.icon;
          return (
            <div key={i} className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 flex items-center gap-4">
              <div className={`${stat.color} text-white p-4 rounded-xl shadow-sm`}>
                <Icon className="w-8 h-8" />
              </div>
              <div>
                <div className="text-3xl font-bold text-gray-900">{stat.value}</div>
                <div className="text-sm font-medium text-gray-500">{stat.label}</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
