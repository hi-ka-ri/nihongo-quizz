import { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { unitApi, vocabApi } from '../services/api';
import type { Unit, Vocabulary } from '../types';
import { ArrowLeft, BookOpen, PenTool, Shuffle, ListOrdered, ChevronLeft, ChevronRight, Menu, X } from 'lucide-react';

export const UnitDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [unit, setUnit] = useState<Unit | null>(null);
  const [vocabularies, setVocabularies] = useState<Vocabulary[]>([]);
  const [originalVocabularies, setOriginalVocabularies] = useState<Vocabulary[]>([]);
  const [loading, setLoading] = useState(true);
  
  // Options state
  const [selectedLimit, setSelectedLimit] = useState<number>(20);
  const [randomize, setRandomize] = useState<boolean>(false);
  
  // Layout state
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    if (id) {
      setLoading(true);
      Promise.all([
        unitApi.getUnitById(Number(id)),
        vocabApi.getVocabByUnit(Number(id))
      ]).then(([unitData, vocabData]) => {
        setUnit(unitData || null);
        setVocabularies(vocabData || []);
        setOriginalVocabularies(vocabData || []);
        setLoading(false);
      });
    }
  }, [id]);

  if (loading) {
    return <div className="flex justify-center items-center h-64"><div className="animate-pulse flex space-x-4"><div className="rounded-full bg-gray-200 h-10 w-10"></div></div></div>;
  }

  if (!unit) {
    return (
      <div className="text-center py-12">
        <h2 className="text-2xl font-bold text-gray-900 mb-4">Unit not found</h2>
        <button onClick={() => navigate('/units')} className="text-primary hover:underline flex items-center justify-center gap-2 mx-auto">
          <ArrowLeft size={16} /> Back to Units
        </button>
      </div>
    );
  }

  const limits = [10, 20, 30, 40, 60, vocabularies.length];

  // Pagination logic
  const itemsPerPage = selectedLimit === vocabularies.length ? (vocabularies.length > 0 ? vocabularies.length : 20) : selectedLimit;
  const totalPages = Math.ceil(vocabularies.length / itemsPerPage);
  
  // Ensure currentPage is valid if totalPages changes
  if (currentPage > totalPages && totalPages > 0) {
    setCurrentPage(1);
  }
  
  const paginatedVocabs = vocabularies.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

  const handlePageChange = (newPage: number) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  const handleLimitChange = (limit: number) => {
    setSelectedLimit(limit);
    setCurrentPage(1); // Reset to first page when limit changes
  };

  const handleRandomize = (isRandom: boolean) => {
    setRandomize(isRandom);
    setCurrentPage(1);
    if (isRandom) {
      setVocabularies([...originalVocabularies].sort(() => Math.random() - 0.5));
    } else {
      setVocabularies([...originalVocabularies]);
    }
  };

  return (
    <div className="max-w-7xl mx-auto pb-12">
      <div className="flex items-center gap-4 mb-6">
        <button onClick={() => navigate('/units')} className="text-gray-500 hover:text-gray-900 flex items-center gap-2 transition-colors">
          <ArrowLeft size={20} /> Back to Units
        </button>
        <button onClick={() => setIsSidebarOpen(!isSidebarOpen)} className="p-2 bg-white border border-gray-200 rounded-md text-gray-600 hover:bg-gray-50 flex items-center gap-2">
          {isSidebarOpen ? <X size={18} /> : <Menu size={18} />}
          <span className="text-sm font-medium">{isSidebarOpen ? 'Đóng tùy chọn' : 'Tùy chọn'}</span>
        </button>
      </div>

      <div className="flex flex-col lg:flex-row gap-6 items-start">
        {/* Left Sidebar for Options & Actions */}
        <div className={`transition-all duration-300 overflow-hidden flex-shrink-0 self-start sticky top-24 ${isSidebarOpen ? 'w-full lg:w-72 opacity-100' : 'w-0 opacity-0 h-0 lg:h-auto'}`}>
          <div className="bg-white rounded-xl p-6 border border-gray-100 space-y-8">
            <div>
              <h3 className="text-lg font-bold text-gray-900 mb-4">{unit.title}</h3>
              
              <div className="space-y-6">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Số lượng từ vựng</label>
                  <div className="flex flex-wrap gap-2">
                    {limits.map(limit => (
                      <button
                        key={limit}
                        onClick={() => handleLimitChange(limit)}
                        className={`px-3 py-1.5 rounded-lg text-sm font-medium transition-colors ${
                          selectedLimit === limit
                            ? 'bg-primary text-white shadow-sm'
                            : 'bg-white border border-gray-200 text-gray-700 hover:bg-gray-50'
                        }`}
                      >
                        {limit === vocabularies.length ? 'Tất cả' : `${limit} từ`}
                      </button>
                    ))}
                  </div>
                </div>
              </div>
            </div>

            {/* Action Buttons in Sidebar */}
            <div className="space-y-3 pt-6 border-t border-gray-100">
              <Link to={`/units/${unit.id}/learn?limit=${selectedLimit}&random=${randomize}`} className="flex flex-row items-center justify-center gap-3 w-full p-4 bg-white hover:bg-gray-50 rounded-xl transition-colors border border-gray-200 shadow-sm text-center">
                <BookOpen className="w-5 h-5 text-gray-600" />
                <span className="font-bold text-gray-900">Học từ vựng</span>
              </Link>
              
              <Link to={`/units/${unit.id}/quiz?limit=${selectedLimit}&random=${randomize}`} className="flex flex-row items-center justify-center gap-3 w-full p-4 bg-white hover:bg-gray-50 rounded-xl transition-colors border border-gray-200 shadow-sm text-center group">
                <PenTool className="w-5 h-5 text-gray-600 group-hover:text-primary transition-colors" />
                <span className="font-bold text-gray-900 group-hover:text-primary transition-colors">Làm Quiz</span>
              </Link>
            </div>
          </div>
        </div>

        {/* Right Main Content */}
        <div className="flex-1 w-full space-y-6 min-w-0">
          {/* Vocabulary List */}
          <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
            <div className="p-6 border-b border-gray-100 bg-gray-50 flex flex-col sm:flex-row sm:items-center justify-between gap-4">
              <div>
                <h2 className="text-xl font-bold text-gray-900">Danh sách từ vựng</h2>
                <span className="text-sm text-gray-500 font-medium">{vocabularies.length} từ</span>
              </div>
              <div className="flex bg-white rounded-lg border border-gray-200 overflow-hidden shadow-sm">
                <button
                  onClick={() => handleRandomize(false)}
                  className={`flex items-center gap-2 px-4 py-2 text-sm font-medium transition-colors ${
                    !randomize ? 'bg-primary/10 text-primary' : 'text-gray-600 hover:bg-gray-50'
                  }`}
                >
                  <ListOrdered size={16} />
                  Mặc định
                </button>
                <div className="w-px bg-gray-200"></div>
                <button
                  onClick={() => handleRandomize(true)}
                  className={`flex items-center gap-2 px-4 py-2 text-sm font-medium transition-colors ${
                    randomize ? 'bg-primary/10 text-primary' : 'text-gray-600 hover:bg-gray-50'
                  }`}
                >
                  <Shuffle size={16} />
                  Ngẫu nhiên
                </button>
              </div>
            </div>
            
            <div className="divide-y divide-gray-100">
              {paginatedVocabs.map((vocab, index) => (
                <div key={vocab.id} className="p-4 sm:p-6 hover:bg-gray-50 transition-colors flex flex-col sm:flex-row sm:items-center gap-4">
                  <div className="flex-shrink-0 w-12 h-12 bg-primary/10 rounded-full flex items-center justify-center text-primary font-bold text-lg">
                    {(currentPage - 1) * itemsPerPage + index + 1}
                  </div>
                  <div className="flex-1 grid grid-cols-1 sm:grid-cols-3 gap-4">
                    <div>
                      <div className="text-2xl font-bold text-gray-900 mb-1">{vocab.kanji || vocab.hiragana}</div>
                      {vocab.kanji && <div className="text-sm text-gray-500">{vocab.hiragana}</div>}
                    </div>
                    <div className="flex items-center">
                      <span className="text-sm font-medium text-primary bg-red-50 px-3 py-1 rounded-full">
                        {vocab.sinoVietnamese || 'Hán Việt'}
                      </span>
                    </div>
                    <div className="flex items-center">
                      <span className="text-gray-700 font-medium">{vocab.meaning}</span>
                    </div>
                  </div>
                </div>
              ))}
              {vocabularies.length === 0 && (
                <div className="p-8 text-center text-gray-500">
                  Không có từ vựng nào trong Unit này.
                </div>
              )}
            </div>

            {/* Pagination Controls */}
            {totalPages > 1 && (
              <div className="p-4 border-t border-gray-100 flex items-center justify-center gap-4 bg-gray-50">
                <button
                  onClick={() => handlePageChange(currentPage - 1)}
                  disabled={currentPage === 1}
                  className="p-2 rounded-lg border border-gray-200 bg-white text-gray-600 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
                >
                  <ChevronLeft size={20} />
                </button>
                <span className="text-sm font-medium text-gray-700">
                  Trang {currentPage} / {totalPages}
                </span>
                <button
                  onClick={() => handlePageChange(currentPage + 1)}
                  disabled={currentPage === totalPages}
                  className="p-2 rounded-lg border border-gray-200 bg-white text-gray-600 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
                >
                  <ChevronRight size={20} />
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

