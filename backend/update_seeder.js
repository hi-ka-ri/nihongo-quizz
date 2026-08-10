const fs = require('fs');
const file = 'd:\\quizz\\backend\\src\\main\\java\\com\\example\\backend\\security\\DatabaseSeeder.java';
let content = fs.readFileSync(file, 'utf8');

const newVocab = `        List<String[]> vocabData = Arrays.asList(
                new String[]{"男性", "だんせい", "Nam giới", "Nam Tính"},
                new String[]{"女性", "じょせい", "Nữ giới", "Nữ Tính"},
                new String[]{"高齢", "こうれい", "Tuổi cao, cao tuổi", "Cao Linh"},
                new String[]{"年上", "としうえ", "Lớn tuổi hơn", "Niên Thượng"},
                new String[]{"目上", "めうえ", "Cấp trên, bề trên", "Mục Thượng"},
                new String[]{"先輩", "せんぱい", "Tiền bối, người đi trước", "Tiên Bối"},
                new String[]{"後輩", "こうはい", "Hậu bối, người đi sau", "Hậu Bối"},
                new String[]{"上司", "じょうし", "Cấp trên, sếp", "Thượng Tư"},
                new String[]{"相手", "あいて", "Đối phương, đối tác", "Tương Thủ"},
                new String[]{"知り合い", "しりあい", "Người quen", "Tri Hợp"},
                new String[]{"友人", "ゆうじん", "Bạn bè", "Hữu Nhân"},
                new String[]{"仲", "なか", "Mối quan hệ", "Trọng"},
                new String[]{"生年月日", "せいねんがっぴ", "Ngày tháng năm sinh", "Sinh Niên Nguyệt Nhật"},
                new String[]{"誕生", "たんじょう", "Sự ra đời", "Đản Sinh"},
                new String[]{"年", "とし", "Năm, tuổi", "Niên"},
                new String[]{"出身", "しゅっしん", "Xuất thân, quê quán", "Xuất Thân"},
                new String[]{"故郷", "こきょう", "Quê hương", "Cố Hương"},
                new String[]{"成長", "せいちょう", "Trưởng thành, khôn lớn", "Thành Trưởng"},
                new String[]{"成人", "せいじん", "Người trưởng thành", "Thành Nhân"},
                new String[]{"合格", "ごうかく", "Thi đỗ, trúng tuyển", "Hợp Cách"},
                new String[]{"進学", "しんがく", "Học lên cao hơn", "Tiến Học"},
                new String[]{"退学", "たいがく", "Bỏ học, thôi học", "Thoái Học"},
                new String[]{"就職", "しゅうしょく", "Tìm việc, nhậm chức", "Tựu Chức"},
                new String[]{"退職", "たいしょく", "Nghỉ việc, từ chức", "Thoái Chức"},
                new String[]{"失業", "しつぎょう", "Thất nghiệp", "Thất Nghiệp"},
                new String[]{"残業", "ざんぎょう", "Làm thêm giờ", "Tàn Nghiệp"},
                new String[]{"生活", "せいかつ", "Sinh hoạt, đời sống", "Sinh Hoạt"},
                new String[]{"通勤", "つうきん", "Đi làm", "Thông Cần"},
                new String[]{"学歴", "がくれき", "Bằng cấp, quá trình học tập", "Học Lịch"},
                new String[]{"給料", "きゅうりょう", "Tiền lương", "Cấp Liệu"},
                new String[]{"面接", "めんせつ", "Phỏng vấn", "Diện Tiếp"},
                new String[]{"休憩", "きゅうけい", "Nghỉ giải lao", "Hưu Khế"},
                new String[]{"観光", "かんこう", "Tham quan, du lịch", "Quan Quang"},
                new String[]{"帰国", "きこく", "Về nước", "Quy Quốc"},
                new String[]{"帰省", "きせい", "Về quê", "Quy Tỉnh"},
                new String[]{"帰宅", "きたく", "Về nhà", "Quy Trạch"},
                new String[]{"参加", "さんか", "Tham gia", "Tham Gia"},
                new String[]{"出席", "しゅっせき", "Có mặt, tham dự", "Xuất Tịch"},
                new String[]{"欠席", "けっせき", "Vắng mặt", "Khiếm Tịch"},
                new String[]{"遅刻", "ちこく", "Đến muộn, trễ giờ", "Trì Khắc"},
                new String[]{"化粧", "けしょう", "Trang điểm", "Hóa Trang"},
                new String[]{"計算", "けいさん", "Tính toán", "Kế Toán"},
                new String[]{"計画", "けいかく", "Kế hoạch", "Kế Hoạch"},
                new String[]{"成功", "せいこう", "Thành công", "Thành Công"},
                new String[]{"失敗", "しっぱい", "Thất bại", "Thất Bại"},
                new String[]{"準備", "じゅんび", "Chuẩn bị", "Chuẩn Bị"},
                new String[]{"整理", "せいり", "Chỉnh lý, sắp xếp", "Chỉnh Lý"},
                new String[]{"注文", "ちゅうもん", "Đặt hàng, gọi món", "Chú Văn"},
                new String[]{"貯金", "ちょきん", "Tiết kiệm tiền", "Trữ Kim"},
                new String[]{"徹夜", "てつや", "Thức trắng đêm", "Triệt Dạ"},
                new String[]{"引っ越し", "ひっこし", "Chuyển nhà", "Dẫn Việt"},
                new String[]{"身長", "しんちょう", "Chiều cao", "Thân Trường"},
                new String[]{"体重", "たいじゅう", "Cân nặng", "Thể Trọng"},
                new String[]{"けが", "けが", "Vết thương, chấn thương", "(Quái Ngã)"},
                new String[]{"会", "かい", "Tiệc, hội", "Hội"},
                new String[]{"趣味", "しゅみ", "Sở thích", "Thú Vị"},
                new String[]{"興味", "きょうみ", "Hứng thú, quan tâm", "Hưng Vị"},
                new String[]{"思い出", "おもいで", "Kỷ niệm", "Tư Xuất"},
                new String[]{"冗談", "じょうだん", "Nói đùa", "Nhũng Đàm"},
                new String[]{"目的", "もくてき", "Mục đích", "Mục Đích"},
                new String[]{"約束", "やくそく", "Lời hứa, cuộc hẹn", "Ước Thúc"},
                new String[]{"おしゃべり", "おしゃべり", "Nói chuyện, người hay nói", "-"},
                new String[]{"遠慮", "えんりょ", "Ngần ngại, khách sáo", "Viễn Lự"},
                new String[]{"我慢", "がまん", "Chịu đựng, nhẫn nhịn", "Ngã Mạn"},
                new String[]{"迷惑", "めいわく", "Phiền toái, làm phiền", "Mê Hoặc"},
                new String[]{"希望", "きぼう", "Hy vọng, mong muốn", "Hy Vọng"},
                new String[]{"夢", "ゆめ", "Giấc mơ, ước mơ", "Mộng"},
                new String[]{"賛成", "さんせい", "Tán thành, đồng ý", "Tán Thành"},
                new String[]{"反対", "はんたい", "Phản đối, ngược lại", "Phản Đối"},
                new String[]{"想像", "そうぞう", "Tưởng tượng", "Tưởng Tượng"},
                new String[]{"努力", "どりょく", "Nỗ lực, cố gắng", "Nỗ Lực"},
                new String[]{"太陽", "たいよう", "Mặt trời", "Thái Dương"},
                new String[]{"地球", "ちきゅう", "Trái đất", "Địa Cầu"},
                new String[]{"温度", "おんど", "Nhiệt độ", "Ôn Độ"},
                new String[]{"湿度", "しつど", "Độ ẩm", "Thấp Độ"},
                new String[]{"湿気", "しっけ", "Hơi ẩm", "Thấp Khí"},
                new String[]{"梅雨", "つゆ", "Mùa mưa", "Mai Vũ"},
                new String[]{"かび", "かび", "Nấm mốc", "-"},
                new String[]{"暖房", "だんぼう", "Lò sưởi, máy sưởi", "Noãn Phòng"},
                new String[]{"皮", "かわ", "Da, vỏ", "Bì"},
                new String[]{"缶", "かん", "Lon, hộp kim loại", "Phữu / Can"},
                new String[]{"画面", "がめん", "Màn hình", "Họa Diện"},
                new String[]{"番組", "ばんぐみ", "Chương trình (TV, radio)", "Phiên Tổ"},
                new String[]{"記事", "きじ", "Ký sự, bài báo", "Ký Sự"},
                new String[]{"近所", "きんじょ", "Hàng xóm, lân cận", "Cận Sở"},
                new String[]{"警察", "けいさつ", "Cảnh sát", "Cảnh Sát"},
                new String[]{"犯人", "はんにん", "Tội phạm, thủ phạm", "Phạm Nhân"},
                new String[]{"小銭", "こぜに", "Tiền lẻ", "Tiểu Tiền"},
                new String[]{"ごちそう", "ごちそう", "Khao, bữa ăn ngon", "(Ngự Trì Tẩu)"},
                new String[]{"作者", "さくしゃ", "Tác giả", "Tác Giả"},
                new String[]{"作品", "さくひん", "Tác phẩm", "Tác Phẩm"},
                new String[]{"制服", "せいふく", "Đồng phục", "Chế Phục"},
                new String[]{"洗剤", "せんざい", "Chất tẩy rửa", "Tẩy Tễ"},
                new String[]{"底", "そこ", "Đáy", "Để"},
                new String[]{"地下", "ちか", "Tầng hầm, dưới đất", "Địa Hạ"},
                new String[]{"寺", "てら", "Chùa", "Tự"},
                new String[]{"道路", "どうろ", "Đường bộ", "Đạo Lộ"},
                new String[]{"坂", "さか", "Dốc", "Phản"},
                new String[]{"煙", "けむり", "Khói", "Yên"},
                new String[]{"灰", "はい", "Tàn, tro", "Khôi"},
                new String[]{"判", "はん", "Con dấu", "Phán"},
                new String[]{"名刺", "めいし", "Danh thiếp", "Danh Thứ"},
                new String[]{"免許", "めんきょ", "Giấy phép", "Miễn Hứa"},
                new String[]{"多く", "おおく", "Nhiều", "Đa"},
                new String[]{"前半", "ぜんはん", "Nửa đầu", "Tiền Bán"},
                new String[]{"後半", "こうはん", "Nửa sau", "Hậu Bán"},
                new String[]{"最高", "さいこう", "Cao nhất, tuyệt vời nhất", "Tối Cao"},
                new String[]{"最低", "さいてい", "Thấp nhất, tồi tệ nhất", "Tối Đê"},
                new String[]{"最初", "さいしょ", "Đầu tiên", "Tối Sơ"},
                new String[]{"最後", "さいご", "Cuối cùng", "Tối Hậu"},
                new String[]{"自動", "じどう", "Tự động", "Tự Động"},
                new String[]{"種類", "しゅるい", "Chủng loại, loại", "Chủng Loại"},
                new String[]{"性格", "せいかく", "Tính cách", "Tính Cách"},
                new String[]{"性質", "せいしつ", "Tính chất", "Tính Chất"},
                new String[]{"順番", "じゅんばん", "Thứ tự, lần lượt", "Thuận Phiên"},
                new String[]{"番", "ばん", "Lượt, số", "Phiên"},
                new String[]{"方法", "ほうほう", "Phương pháp", "Phương Pháp"},
                new String[]{"製品", "せいひん", "Sản phẩm", "Chế Phẩm"},
                new String[]{"値上がり", "ねあがり", "Tăng giá", "Trị Thượng"},
                new String[]{"生", "なま", "Tươi sống, nguyên chất", "Sinh"}
        );

        for (String[] data : vocabData) {
            Vocabulary voc = Vocabulary.builder()
                    .unit(unit1)
                    .kanji(data[0])
                    .hiragana(data[1])
                    .romaji(data[1]) 
                    .meaning(data[2])
                    .sinoVietnamese(data.length > 3 ? data[3] : "")
                    .build();
            vocabularyRepository.save(voc);
        }

        System.out.println("====== Cleared old and Seeded Unit 1 with 120 vocabularies ======");

        // Seed Questions
        questionRepository.deleteAll();
        seedQuestionsForUnit(unit1);
        System.out.println("====== Seeded Questions for Unit 1 ======");`;

const lines = content.split('\r\n'); // try \r\n if \n doesn't work, actually we can just match by substring
const startStr = '        List<String[]> vocabData = Arrays.asList(';
const endStr = 'System.out.println("====== Seeded Questions for Unit 1, Unit 2 and Unit 3 ======");';

const startIdx = content.indexOf(startStr);
const endIdx = content.indexOf(endStr);

if(startIdx !== -1 && endIdx !== -1) {
    const before = content.substring(0, startIdx);
    const after = content.substring(endIdx + endStr.length);
    const newContent = before + newVocab + '\n' + after;
    fs.writeFileSync(file, newContent, 'utf8');
    console.log('Success');
} else {
    console.log('Failed to find indices', startIdx, endIdx);
}
