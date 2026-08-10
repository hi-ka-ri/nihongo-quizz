
import { useLocation, useNavigate, Navigate } from 'react-router-dom';
import type { Question, QuizAttempt } from '../types';
import { Trophy, Target, ArrowLeft, RefreshCw, XCircle } from 'lucide-react';

export const QuizResult = () => {
  const location = useLocation();
  const navigate = useNavigate();
  
  const state = location.state as {
    attempt: QuizAttempt;
    questions: Question[];
    answers: Record<string, string>;
  };

  if (!state || !state.attempt) {
    return <Navigate to="/units" replace />;
  }

  const { attempt } = state;
  const percentage = Math.round((attempt.score / attempt.totalQuestions) * 100);
  const incorrect = attempt.totalQuestions - attempt.score;

  return (
    <div className="max-w-3xl mx-auto py-12">
      <div className="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden">
        
        {/* Header Banner */}
        <div className="bg-gray-900 px-8 py-12 text-center text-white relative overflow-hidden">
          <div className="absolute top-0 left-0 w-full h-full opacity-10 bg-[radial-gradient(ellipse_at_center,_var(--tw-gradient-stops))] from-white via-transparent to-transparent"></div>
          
          <Trophy className={`w-20 h-20 mx-auto mb-6 ${percentage >= 80 ? 'text-yellow-400' : 'text-gray-400'}`} />
          <h1 className="text-4xl font-black mb-2">
            {percentage >= 80 ? 'Tuyệt vời!' : percentage >= 50 ? 'Khá tốt!' : 'Cần cố gắng thêm!'}
          </h1>
          <p className="text-gray-400 font-medium">Bạn đã hoàn thành bài kiểm tra</p>
        </div>

        {/* Stats Grid */}
        <div className="p-8">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-10">
            <div className="bg-gray-50 rounded-2xl p-6 text-center border border-gray-100">
              <div className="text-gray-500 text-sm font-bold mb-2 flex items-center justify-center gap-1">
                <Target size={16} /> Điểm
              </div>
              <div className="text-3xl font-black text-gray-900">{attempt.score}<span className="text-lg text-gray-400">/{attempt.totalQuestions}</span></div>
            </div>
            
            <div className="bg-red-50 rounded-2xl p-6 text-center border border-red-100">
              <div className="text-red-500 text-sm font-bold mb-2">Tỷ lệ</div>
              <div className="text-3xl font-black text-primary">{percentage}%</div>
            </div>
            
            <div className="bg-green-50 rounded-2xl p-6 text-center border border-green-100">
              <div className="text-green-600 text-sm font-bold mb-2">Đúng</div>
              <div className="text-3xl font-black text-green-700">{attempt.score}</div>
            </div>
            
            <div className="bg-orange-50 rounded-2xl p-6 text-center border border-orange-100">
              <div className="text-orange-600 text-sm font-bold mb-2">Sai</div>
              <div className="text-3xl font-black text-orange-700">{incorrect}</div>
            </div>
          </div>

          <div className="w-full bg-gray-100 rounded-full h-4 mb-12 overflow-hidden flex">
            <div className="bg-green-500 h-full" style={{ width: `${percentage}%` }}></div>
            <div className="bg-orange-400 h-full" style={{ width: `${100 - percentage}%` }}></div>
          </div>

          {/* Action Buttons */}
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <button 
              onClick={() => navigate(`/units/${attempt.unitId}`)}
              className="px-8 py-4 rounded-xl font-bold text-gray-700 bg-gray-100 hover:bg-gray-200 transition-colors flex items-center justify-center gap-2"
            >
              <ArrowLeft size={20} /> Về Unit
            </button>
            
            {incorrect > 0 && (
              <button 
                onClick={() => alert('Review mode coming soon!')}
                className="px-8 py-4 rounded-xl font-bold text-orange-600 bg-orange-50 border border-orange-200 hover:bg-orange-100 transition-colors flex items-center justify-center gap-2"
              >
                <XCircle size={20} /> Ôn từ sai
              </button>
            )}
            
            <button 
              onClick={() => navigate(`/units/${attempt.unitId}/quiz`)}
              className="px-8 py-4 rounded-xl font-bold text-white bg-primary hover:bg-primary-dark transition-colors flex items-center justify-center gap-2 shadow-md shadow-red-200"
            >
              <RefreshCw size={20} /> Làm lại
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
