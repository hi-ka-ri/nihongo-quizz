package com.example.backend.security;

import com.example.backend.entity.Unit;
import com.example.backend.entity.User;
import com.example.backend.entity.Vocabulary;
import com.example.backend.entity.enums.Role;
import com.example.backend.repository.UnitRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.VocabularyRepository;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final VocabularyRepository vocabularyRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed Admin
        User admin = userRepository.findByUsername("admin").orElse(null);
        if (admin == null) {
            admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("nguyenanh194699@gmail.com")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("====== Admin account created: admin / admin123 ======");
        } else {
            // Force update email to ensure OTP works if it was created with an old email
            if (!"nguyenanh194699@gmail.com".equals(admin.getEmail())) {
                admin.setEmail("nguyenanh194699@gmail.com");
                userRepository.save(admin);
                System.out.println("====== Admin email updated to nguyenanh194699@gmail.com ======");
            }
        }

        // Only seed if empty to prevent data loss on restart
        if (vocabularyRepository.count() == 0) {

        Unit unit1;
        if (unitRepository.count() == 0) {
            unit1 = Unit.builder()
                    .title("Unit 1: Con người & Cuộc sống")
                    .description("40 từ vựng N3 về chủ đề con người, mối quan hệ và các sự kiện trong đời sống.")
                    .orderIndex(1)
                    .imageUrl("/assets/hikari_logo.png")
                    .build();
            unitRepository.save(unit1);
        } else {
            unit1 = unitRepository.findAll().get(0);
        }

        // Apply default image for all units missing one
        for (Unit unit : unitRepository.findAll()) {
            if (unit.getImageUrl() == null || unit.getImageUrl().trim().isEmpty()) {
                unit.setImageUrl("/assets/hikari_logo.png");
                unitRepository.save(unit);
            }
        }

        List<String[]> vocabData = Arrays.asList(
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

        // Seed Unit 2
        Unit unit2;
        if (unitRepository.count() < 2) {
            unit2 = Unit.builder()
                    .title("Unit 2: Động từ 1")
                    .description("100 từ vựng N3 về chủ đề động từ phần 1.")
                    .orderIndex(2)
                    .imageUrl("/assets/hikari_logo.png")
                    .build();
            unitRepository.save(unit2);
        } else {
            unit2 = unitRepository.findAll().get(1);
        }

        List<String[]> vocabData2 = Arrays.asList(
                new String[]{"渇く", "かわく", "Khát nước", "Khát"},
                new String[]{"嗅ぐ", "かぐ", "Ngửi", "Khứu"},
                new String[]{"叩く", "たたく", "Đánh, vỗ, gõ", "Khấu"},
                new String[]{"殴る", "なぐる", "Đấm", "Ẩu"},
                new String[]{"ける", "ける", "Đá", ""},
                new String[]{"抱く", "だく", "Ôm, ẵm", "Bão"},
                new String[]{"倒れる", "たおれる", "Ngã, đổ, bệnh", "Đảo"},
                new String[]{"倒す", "たおす", "Làm đổ, đánh bại", "Đảo"},
                new String[]{"起きる・起こる", "おきる・おこる", "Thức dậy, xảy ra", "Khởi"},
                new String[]{"起こす", "おこす", "Đánh thức, gây ra", "Khởi"},
                new String[]{"尋ねる", "たずねる", "Hỏi", "Tầm"},
                new String[]{"呼ぶ", "よぶ", "Gọi", "Hô"},
                new String[]{"叫ぶ", "さけぶ", "Kêu, la hét", "Khiếu"},
                new String[]{"黙る", "だまる", "Im lặng", "Mặc"},
                new String[]{"飼う", "かう", "Nuôi (động vật)", "Tự"},
                new String[]{"数える", "かぞえる", "Đếm", "Số"},
                new String[]{"乾く", "かわく", "Khô", "Can"},
                new String[]{"乾かす", "かわかす", "Làm khô, sấy khô", "Can"},
                new String[]{"畳む", "たたむ", "Gấp, xếp", "Điệp"},
                new String[]{"誘う", "さそう", "Mời, rủ rê", "Dụ"},
                new String[]{"おごる", "おごる", "Khao, chiêu đãi", ""},
                new String[]{"預かる", "あずかる", "Trông nom, chăm sóc", "Dự"},
                new String[]{"預ける", "あずける", "Gửi gắm, giao phó", "Dự"},
                new String[]{"決まる", "きまる", "Được quyết định", "Quyết"},
                new String[]{"決める", "きめる", "Quyết định", "Quyết"},
                new String[]{"写る", "うつる", "Được chụp, chiếu", "Tả"},
                new String[]{"写す", "うつす", "Chụp, sao chép", "Tả"},
                new String[]{"思い出す", "おもいだす", "Nhớ lại", "Tư Xuất"},
                new String[]{"教わる", "おそわる", "Được dạy, học được", "Giáo"},
                new String[]{"申し込む", "もうしこむ", "Đăng ký", "Thân Vào"},
                new String[]{"断る", "ことわる", "Từ chối", "Đoạn"},
                new String[]{"見つかる", "みつかる", "Được tìm thấy", "Kiến"},
                new String[]{"見つける", "みつける", "Tìm thấy", "Kiến"},
                new String[]{"捕まる", "つかまる", "Bị bắt", "Bộ"},
                new String[]{"捕まえる", "つかまえる", "Bắt, tóm", "Bộ"},
                new String[]{"乗る", "のる", "Lên xe, cỡi", "Thừa"},
                new String[]{"乗せる", "のせる", "Cho lên xe, chất lên", "Thừa"},
                new String[]{"降りる・下りる", "おりる", "Xuống (xe, núi)", "Giáng / Hạ"},
                new String[]{"降ろす・下ろす", "おろす", "Cho xuống, hạ xuống", "Giáng / Hạ"},
                new String[]{"直る", "なおる", "Được sửa chữa", "Trực"},
                new String[]{"直す", "なおす", "Sửa chữa", "Trực"},
                new String[]{"治る", "なおる", "Khỏi (bệnh)", "Trị"},
                new String[]{"治す", "なおす", "Chữa (bệnh)", "Trị"},
                new String[]{"亡くなる", "なくなる", "Mất, qua đời", "Vong"},
                new String[]{"亡くす", "なくす", "Mất (người thân)", "Vong"},
                new String[]{"生まれる", "うまれる", "Được sinh ra", "Sinh"},
                new String[]{"生む・産む", "うむ", "Sinh, đẻ", "Sinh / Sản"},
                new String[]{"出会う", "であう", "Gặp gỡ (tình cờ)", "Xuất Hội"},
                new String[]{"訪ねる", "たずねる", "Đến thăm", "Phỏng"},
                new String[]{"付き合う", "つきあう", "Hẹn hò, giao du", "Phó Hợp"},
                new String[]{"効く", "きく", "Có hiệu quả", "Hiệu"},
                new String[]{"はやる", "はやる", "Phổ biến, thịnh hành", ""},
                new String[]{"経つ", "たつ", "Trôi qua (thời gian)", "Kinh"},
                new String[]{"間に合う", "まにあう", "Kịp thời gian", "Gian Hợp"},
                new String[]{"間に合わせる", "まにあわせる", "Làm cho kịp", "Gian Hợp"},
                new String[]{"通う", "かよう", "Đi lại thường xuyên", "Thông"},
                new String[]{"込む", "こむ", "Đông đúc", "Vào"},
                new String[]{"すれ違う", "すれちがう", "Đi lướt qua nhau", "Vi"},
                new String[]{"掛かる", "かかる", "Tốn (thời gian, tiền), treo", "Quải"},
                new String[]{"掛ける", "かける", "Treo, gọi (điện thoại)", "Quải"},
                new String[]{"動く", "うごく", "Di chuyển, hoạt động", "Động"},
                new String[]{"動かす", "うごかす", "Làm chuyển động", "Động"},
                new String[]{"離れる", "はなれる", "Cách xa, rời xa", "Ly"},
                new String[]{"離す", "はなす", "Tách ra, buông ra", "Ly"},
                new String[]{"ぶつかる", "ぶつかる", "Va chạm, đụng", ""},
                new String[]{"ぶつける", "ぶつける", "Đâm vào, húc vào", ""},
                new String[]{"こぼれる", "こぼれる", "Bị tràn, đổ ra", ""},
                new String[]{"こぼす", "こぼす", "Làm tràn, làm đổ", ""},
                new String[]{"ふく", "ふく", "Lau, chùi", ""},
                new String[]{"片付く", "かたづく", "Được dọn dẹp", "Phiến Phó"},
                new String[]{"片付ける", "かたづける", "Dọn dẹp", "Phiến Phó"},
                new String[]{"包む", "つつむ", "Gói, bọc", "Bao"},
                new String[]{"張る", "はる", "Dán, căng ra", "Trương"},
                new String[]{"無くなる", "なくなる", "Bị mất, hết", "Vô"},
                new String[]{"無くす", "なくす", "Làm mất", "Vô"},
                new String[]{"足りる", "たりる", "Đủ", "Túc"},
                new String[]{"残る", "のこる", "Còn lại", "Tàn"},
                new String[]{"残す", "のこす", "Bỏ lại, chừa lại", "Tàn"},
                new String[]{"腐る", "くさる", "Thiu, thối, mục nát", "Hủ"},
                new String[]{"むける", "むける", "Bị lột, bong tróc", ""},
                new String[]{"むく", "むく", "Bóc, lột (vỏ)", ""},
                new String[]{"滑る", "すべる", "Trượt", "Hoạt"},
                new String[]{"積もる", "つもる", "Tích tụ, chất đống", "Tích"},
                new String[]{"積む", "つむ", "Chất lên, tích lũy", "Tích"},
                new String[]{"空く", "あく", "Trống, rỗng, rảnh rỗi", "Không"},
                new String[]{"空ける", "あける", "Làm trống, đục lỗ", "Không"},
                new String[]{"下がる", "さがる", "Giảm, đi xuống", "Hạ"},
                new String[]{"下げる", "さげる", "Làm giảm, hạ xuống", "Hạ"},
                new String[]{"冷える", "ひえる", "Bị lạnh đi", "Lãnh"},
                new String[]{"冷やす", "ひやす", "Làm lạnh, ướp lạnh", "Lãnh"},
                new String[]{"冷める", "さめる", "Nguội đi", "Lãnh"},
                new String[]{"冷ます", "さます", "Làm nguội", "Lãnh"},
                new String[]{"燃える", "もえる", "Cháy", "Nhiên"},
                new String[]{"燃やす", "もやす", "Đốt cháy", "Nhiên"},
                new String[]{"沸く", "わく", "Sôi lên", "Phí"},
                new String[]{"沸かす", "わかす", "Đun sôi", "Phí"},
                new String[]{"鳴る", "なる", "Kêu, reo", "Minh"},
                new String[]{"鳴らす", "ならす", "Bấm (còi), làm kêu", "Minh"},
                new String[]{"役立つ", "やくだつ", "Có ích, hữu ích", "Dịch Lập"},
                new String[]{"役立てる", "やくだてる", "Ứng dụng, làm cho có ích", "Dịch Lập"}
        );

        for (String[] data : vocabData2) {
            Vocabulary voc = Vocabulary.builder()
                    .unit(unit2)
                    .kanji(data[0])
                    .hiragana(data[1])
                    .romaji(data[1]) 
                    .meaning(data[2])
                    .sinoVietnamese(data[3])
                    .build();
            vocabularyRepository.save(voc);
        }

        System.out.println("====== Cleared old and Seeded Unit 2 with 100 vocabularies ======");

        // Seed Unit 3
        Unit unit3;
        if (unitRepository.count() < 3) {
            unit3 = Unit.builder()
                    .title("Unit 3: Từ loại khác & Danh từ")
                    .description("120 từ vựng N3 về các chủ đề đa dạng.")
                    .orderIndex(3)
                    .imageUrl("/assets/hikari_logo.png")
                    .build();
            unitRepository.save(unit3);
        } else {
            unit3 = unitRepository.findAll().get(2);
        }

        List<String[]> vocabData3 = Arrays.asList(
                new String[]{"飾り", "かざり", "Sự trang trí, đồ trang trí", "Sức"},
                new String[]{"遊び", "あそび", "Trò chơi, sự vui chơi", "Du"},
                new String[]{"集まり", "あつまり", "Cuộc họp, sự tụ tập", "Tập"},
                new String[]{"教え", "おしえ", "Lời dạy, giáo lý", "Giáo"},
                new String[]{"頼み", "たのみ", "Lời nhờ vả, yêu cầu", "Lại"},
                new String[]{"苦労", "くろう", "Gian khổ, vất vả", "Khổ Lao"},
                new String[]{"世話", "せわ", "Chăm sóc, giúp đỡ", "Thế Thoại"},
                new String[]{"応援", "おうえん", "Cổ vũ, hỗ trợ", "Ứng Viện"},
                new String[]{"期待", "きたい", "Kỳ vọng, mong đợi", "Kỳ Đãi"},
                new String[]{"感謝", "かんしゃ", "Cảm tạ, biết ơn", "Cảm Tạ"},
                new String[]{"悩み", "なやみ", "Sự phiền não, trăn trở", "Não"},
                new String[]{"迷い", "まよい", "Sự băn khoăn, lạc lối", "Mê"},
                new String[]{"違い", "ちがい", "Sự khác biệt", "Vi"},
                new String[]{"間違い", "まちがい", "Lỗi lầm, sai sót", "Gian Vi"},
                new String[]{"怒り", "いかり", "Sự tức giận", "Nộ"},
                new String[]{"祈り", "いのり", "Lời cầu nguyện", "Kỳ"},
                new String[]{"祭り", "まつり", "Lễ hội", "Tế"},
                new String[]{"願い", "ねがい", "Lời thỉnh cầu, ước nguyện", "Nguyện"},
                new String[]{"助け", "たすけ", "Sự giúp đỡ", "Trợ"},
                new String[]{"休み", "やすみ", "Sự nghỉ ngơi, ngày nghỉ", "Hưu"},
                new String[]{"考え", "かんがえ", "Suy nghĩ, ý tưởng", "Khảo"},
                new String[]{"戻り", "もどり", "Sự quay lại, tiền trả lại", "Lệ"},
                new String[]{"変わり", "かわり", "Sự thay đổi", "Biến"},
                new String[]{"働き", "はたらき", "Chức năng, hoạt động", "Động"},
                new String[]{"知らせ", "しらせ", "Thông báo, tin tức", "Tri"},
                new String[]{"喜び", "よろこび", "Niềm vui", "Hỷ"},
                new String[]{"笑い", "わらい", "Tiếng cười, nụ cười", "Tiếu"},
                new String[]{"驚き", "おどろき", "Sự ngạc nhiên", "Kinh"},
                new String[]{"悲しみ", "かなしみ", "Nỗi buồn", "Bi"},
                new String[]{"幸せ", "しあわせ", "Hạnh phúc", "Hạnh"},
                new String[]{"得意", "とくい", "Giỏi, tự hào", "Đắc Ý"},
                new String[]{"苦手", "にがて", "Kém, yếu", "Khổ Thủ"},
                new String[]{"熱心", "ねっしん", "Nhiệt tình", "Nhiệt Tâm"},
                new String[]{"夢中", "むちゅう", "Say sưa, đam mê", "Mộng Trung"},
                new String[]{"退屈", "たいくつ", "Chán ngắt, tẻ nhạt", "Thoái Khuất"},
                new String[]{"健康", "けんこう", "Khỏe mạnh", "Kiện Khang"},
                new String[]{"苦しい", "くるしい", "Đau khổ, chật vật", "Khổ"},
                new String[]{"平気", "へいき", "Bình thản, không sao", "Bình Khí"},
                new String[]{"悔しい", "くやしい", "Tiếc nuối, cay cú", "Hối"},
                new String[]{"羨ましい", "うらやましい", "Ghen tị, thèm muốn", "Tiện"},
                new String[]{"かゆい", "かゆい", "Ngứa", ""},
                new String[]{"おとなしい", "おとなしい", "Hiền lành, trầm tính", ""},
                new String[]{"我慢強い", "がまんづよい", "Giỏi chịu đựng", "Ngã Mạn Cường"},
                new String[]{"正直", "しょうじき", "Trung thực", "Chính Trực"},
                new String[]{"けち", "けち", "Keo kiệt", ""},
                new String[]{"わがまま", "わがまま", "Ích kỷ, bướng bỉnh", ""},
                new String[]{"積極的", "せっきょくてき", "Có tính tích cực", "Tích Cực Đích"},
                new String[]{"消極的", "しょうきょくてき", "Có tính tiêu cực", "Tiêu Cực Đích"},
                new String[]{"満足", "まんぞく", "Thỏa mãn, hài lòng", "Mãn Túc"},
                new String[]{"不満", "ふまん", "Bất mãn", "Bất Mãn"},
                new String[]{"不安", "ふあん", "Bất an", "Bất An"},
                new String[]{"大変", "たいへん", "Khó khăn, vất vả", "Đại Biến"},
                new String[]{"無理", "むり", "Quá sức, vô lý", "Vô Lý"},
                new String[]{"不注意", "ふちゅうい", "Bất cẩn", "Bất Chú Ý"},
                new String[]{"楽", "らく", "Nhàn nhã, thoải mái", "Lạc"},
                new String[]{"面倒", "めんどう", "Phiền phức", "Diện Đảo"},
                new String[]{"失礼", "しつれい", "Thất lễ", "Thất Lễ"},
                new String[]{"当然", "とうぜん", "Đương nhiên", "Đương Nhiên"},
                new String[]{"意外", "いがい", "Ngoài dự tính, bất ngờ", "Ý Ngoại"},
                new String[]{"結構", "けっこう", "Khá tốt, đủ rồi", "Kết Cấu"},
                new String[]{"派手", "はで", "Lòe loẹt, sặc sỡ", "Phái Thủ"},
                new String[]{"地味", "じみ", "Giản dị, mộc mạc", "Địa Vị"},
                new String[]{"おしゃれ", "おしゃれ", "Sành điệu, thời trang", ""},
                new String[]{"変", "へん", "Lạ, kỳ quái", "Biến"},
                new String[]{"不思議", "ふしぎ", "Kỳ lạ, huyền bí", "Bất Tư Nghị"},
                new String[]{"まし", "まし", "Tốt hơn (một chút)", ""},
                new String[]{"無駄", "むだ", "Lãng phí, vô ích", "Vô Đà"},
                new String[]{"自由", "じゆう", "Tự do", "Tự Do"},
                new String[]{"不自由", "ふじゆう", "Bất tiện, tàn tật", "Bất Tự Do"},
                new String[]{"暖まる", "あたたまる", "Trở nên ấm áp", "Noãn"},
                new String[]{"暖める", "あたためる", "Làm ấm", "Noãn"},
                new String[]{"高まる", "たかまる", "Tăng lên, cao lên", "Cao"},
                new String[]{"高める", "たかめる", "Nâng cao", "Cao"},
                new String[]{"強まる", "つよまる", "Mạnh lên", "Cường"},
                new String[]{"強める", "つよめる", "Làm mạnh thêm", "Cường"},
                new String[]{"弱まる", "よわまる", "Yếu đi", "Nhược"},
                new String[]{"弱める", "よわめる", "Làm yếu đi", "Nhược"},
                new String[]{"広がる", "ひろがる", "Mở rộng, lan rộng", "Quảng"},
                new String[]{"広げる", "ひろげる", "Làm rộng ra", "Quảng"},
                new String[]{"深まる", "ふかまる", "Sâu sắc thêm", "Thâm"},
                new String[]{"深める", "ふかめる", "Làm sâu sắc thêm", "Thâm"},
                new String[]{"世話", "せわ", "Sự chăm sóc", "Thế Thoại"},
                new String[]{"家庭", "かてい", "Gia đình", "Gia Đình"},
                new String[]{"協力", "きょうりょく", "Hợp tác", "Hiệp Lực"},
                new String[]{"感謝", "かんしゃ", "Sự biết ơn", "Cảm Tạ"},
                new String[]{"迷惑", "めいわく", "Sự phiền phức", "Mê Hoặc"},
                new String[]{"挨拶", "あいさつ", "Chào hỏi", "Nhai Lạt"},
                new String[]{"謝る", "あやまる", "Xin lỗi", "Tạ"},
                new String[]{"お辞儀", "おじぎ", "Cúi chào", "Từ Nghi"},
                new String[]{"握手", "あくしゅ", "Bắt tay", "Ác Thủ"},
                new String[]{"意地悪", "いじわる", "Xấu tính", "Ý Địa Ác"},
                new String[]{"いたずら", "いたずら", "Nghịch ngợm", ""},
                new String[]{"節約", "せつやく", "Tiết kiệm", "Tiết Ước"},
                new String[]{"経営", "けいえい", "Kinh doanh", "Kinh Doanh"},
                new String[]{"反省", "はんせい", "Kiểm điểm lại mình", "Phản Tỉnh"},
                new String[]{"実行", "じっこう", "Thực hành, tiến hành", "Thực Hành"},
                new String[]{"進歩", "しんぽ", "Tiến bộ", "Tiến Bộ"},
                new String[]{"変化", "へんか", "Thay đổi", "Biến Hóa"},
                new String[]{"発達", "はったつ", "Phát triển", "Phát Đạt"},
                new String[]{"体力", "たいりょく", "Thể lực", "Thể Lực"},
                new String[]{"出場", "しゅつじょう", "Tham dự, ra sân", "Xuất Trường"},
                new String[]{"活躍", "かつやく", "Hoạt động sôi nổi", "Hoạt Dược"},
                new String[]{"競争", "きょうそう", "Cạnh tranh", "Cạnh Tranh"},
                new String[]{"応援", "おうえん", "Cổ vũ", "Ứng Viện"},
                new String[]{"拍手", "はくしゅ", "Vỗ tay", "Phách Thủ"},
                new String[]{"人気", "にんき", "Được hâm mộ", "Nhân Khí"},
                new String[]{"噂", "うわさ", "Tin đồn", "Đồn"},
                new String[]{"情報", "じょうほう", "Thông tin", "Tình Báo"},
                new String[]{"交換", "こうかん", "Trao đổi", "Giao Hoán"},
                new String[]{"流行", "りゅうこう", "Lưu hành, thịnh hành", "Lưu Hành"},
                new String[]{"宣伝", "せんでん", "Tuyên truyền", "Tuyên Truyền"},
                new String[]{"広告", "こうこく", "Quảng cáo", "Quảng Cáo"},
                new String[]{"注目", "ちゅうもく", "Chú ý", "Chú Mục"},
                new String[]{"通訳", "つうやく", "Phiên dịch (nói)", "Thông Dịch"},
                new String[]{"翻訳", "ほんやく", "Biên dịch (viết)", "Phiên Dịch"},
                new String[]{"伝言", "でんごん", "Lời nhắn", "Truyền Ngôn"},
                new String[]{"報告", "ほうこく", "Báo cáo", "Báo Cáo"},
                new String[]{"録画", "ろくが", "Ghi hình", "Lục Họa"},
                new String[]{"混雑", "こんざつ", "Tắc nghẽn, đông đúc", "Hỗn Tạp"},
                new String[]{"渋滞", "じゅうたい", "Tắc nghẽn giao thông", "Sáp Trệ"}
        );

        for (String[] data : vocabData3) {
            Vocabulary voc = Vocabulary.builder()
                    .unit(unit3)
                    .kanji(data[0])
                    .hiragana(data[1])
                    .romaji(data[1]) 
                    .meaning(data[2])
                    .sinoVietnamese(data[3])
                    .build();
            vocabularyRepository.save(voc);
        }

        System.out.println("====== Cleared old and Seeded Unit 3 with 120 vocabularies ======");

        // Seed Unit 4
        Unit unit4;
        if (unitRepository.count() < 4) {
            unit4 = Unit.builder()
                    .title("Unit 4: Tính từ & Phó từ")
                    .description("82 từ vựng N3 về chủ đề tính từ và phó từ.")
                    .orderIndex(4)
                    .imageUrl("/assets/hikari_logo.png")
                    .build();
            unitRepository.save(unit4);
        } else {
            unit4 = unitRepository.findAll().get(3);
        }

        List<String[]> vocabData4 = Arrays.asList(
                new String[]{"濃い", "こい", "Đậm, đặc", "Nùng"},
                new String[]{"薄い", "うすい", "Mỏng, nhạt", "Bạc"},
                new String[]{"酸っぱい", "すっぱい", "Chua", "Toan"},
                new String[]{"臭い", "くさい", "Hôi, thối", "Xú"},
                new String[]{"おかしい", "おかしい", "Buồn cười, kỳ lạ", "Khả Tiếu"},
                new String[]{"かっこいい", "かっこいい", "Đẹp trai, bảnh bao", ""},
                new String[]{"うまい", "うまい", "Ngon, giỏi, suôn sẻ", "Mỹ"},
                new String[]{"親しい", "したしい", "Thân thiết", "Thân"},
                new String[]{"詳しい", "くわしい", "Cụ thể, chi tiết", "Tường"},
                new String[]{"細かい", "こまかい", "Nhỏ, lẻ, chi tiết", "Tế"},
                new String[]{"浅い", "あさい", "Nông, cạn", "Thiển"},
                new String[]{"固い・硬い", "かたい", "Cứng", "Cố/Ngạnh"},
                new String[]{"ぬるい", "ぬるい", "Nguội, ấm ấm", "Ôn"},
                new String[]{"まぶしい", "まぶしい", "Chói mắt", "Huyễn"},
                new String[]{"蒸し暑い", "むしあつい", "Oi bức", "Chưng Thử"},
                new String[]{"清潔な", "せいけつな", "Sạch sẽ", "Thanh Khiết"},
                new String[]{"新鮮な", "しんせんな", "Tươi mới", "Tân Tiên"},
                new String[]{"豊かな", "ゆたかな", "Phong phú, giàu có", "Phong"},
                new String[]{"立派な", "りっぱな", "Tuyệt vời, hoành tráng", "Lập Phái"},
                new String[]{"正確な", "せいかくな", "Chính xác", "Chính Xác"},
                new String[]{"確かな", "たしかな", "Chắc chắn, đích thực", "Xác"},
                new String[]{"重要な", "じゅうような", "Quan trọng", "Trọng Yếu"},
                new String[]{"必要な", "ひつような", "Cần thiết", "Tất Yếu"},
                new String[]{"もったいない", "もったいない", "Lãng phí", ""},
                new String[]{"すごい", "すごい", "Tuyệt vời, kinh khủng", ""},
                new String[]{"ひどい", "ひどい", "Tồi tệ, khủng khiếp", ""},
                new String[]{"激しい", "はげしい", "Mãnh liệt, dữ dội", "Khích"},
                new String[]{"そっくりな", "そっくりな", "Giống hệt nhau", ""},
                new String[]{"急な", "きゅうな", "Đột ngột, gấp gáp", "Cấp"},
                new String[]{"適当な", "てきとうな", "Thích hợp, hời hợt", "Thích Đương"},
                new String[]{"特別な", "とくべつな", "Đặc biệt", "Đặc Biệt"},
                new String[]{"完全な", "かんぜんな", "Hoàn toàn", "Hoàn Toàn"},
                new String[]{"盛んな", "さかんな", "Thịnh vượng, phổ biến", "Thịnh"},
                new String[]{"様々な", "さまざまな", "Đa dạng, nhiều loại", "Dạng"},
                new String[]{"可能な", "かのうな", "Có thể, khả thi", "Khả Năng"},
                new String[]{"不可能な", "ふかのうな", "Không thể", "Bất Khả Năng"},
                new String[]{"基本的な", "きほんてきな", "Tính cơ bản", "Cơ Bản Đích"},
                new String[]{"国際的な", "こくさいてきな", "Tính quốc tế", "Quốc Tế Đích"},
                new String[]{"ばらばらな", "ばらばらな", "Lộn xộn, rời rạc", ""},
                new String[]{"ぼろぼろな", "ぼろぼろな", "Rách nát, tơi tả", ""},
                new String[]{"非常に", "ひじょうに", "Vô cùng, rất", "Phi Thường"},
                new String[]{"大変に", "たいへんに", "Rất, cực kỳ", "Đại Biến"},
                new String[]{"ほとんど", "ほとんど", "Hầu hết, hầu như", ""},
                new String[]{"大体", "だいたい", "Đại khái", "Đại Thể"},
                new String[]{"かなり", "かなり", "Khá, tương đối", ""},
                new String[]{"ずいぶん", "ずいぶん", "Đáng kể, nhiều", ""},
                new String[]{"けっこう", "けっこう", "Khá là", ""},
                new String[]{"大分", "だいぶ", "Phần lớn, đáng kể", "Đại Phân"},
                new String[]{"もっと", "もっと", "Hơn nữa", ""},
                new String[]{"すっかり", "すっかり", "Hoàn toàn, toàn bộ", ""},
                new String[]{"一杯", "いっぱい", "Đầy", "Nhất Bôi"},
                new String[]{"ぎりぎり", "ぎりぎり", "Sát nút, vừa vặn", ""},
                new String[]{"ぴったり", "ぴったり", "Vừa khít, hợp", ""},
                new String[]{"たいてい", "たいてい", "Thông thường", ""},
                new String[]{"同時に", "どうじに", "Cùng lúc", "Đồng Thời"},
                new String[]{"前もって", "まえもって", "(Chuẩn bị) trước", "Tiền"},
                new String[]{"すぐに", "すぐに", "Ngay lập tức", ""},
                new String[]{"もうすぐ", "もうすぐ", "Sắp sửa", ""},
                new String[]{"突然", "とつぜん", "Đột nhiên", "Đột Nhiên"},
                new String[]{"あっという間に", "あっというまに", "Trong nháy mắt", "Gian"},
                new String[]{"いつの間にか", "いつのまにか", "Chẳng mấy chốc", "Gian"},
                new String[]{"しばらく", "しばらく", "Một chốc, một lát", ""},
                new String[]{"ずっと", "ずっと", "Suốt, hơn hẳn", ""},
                new String[]{"相変わらず", "あいかわらず", "Như mọi khi", "Tương Biến"},
                new String[]{"次々に", "つぎつぎに", "Lần lượt", "Thứ"},
                new String[]{"どんどん", "どんどん", "Dần dần, đều đặn", ""},
                new String[]{"ますます", "ますます", "Ngày càng", ""},
                new String[]{"やっと", "やっと", "Cuối cùng thì (kết quả tốt)", ""},
                new String[]{"とうとう", "とうとう", "Cuối cùng (kết quả xấu/tốt)", ""},
                new String[]{"ついに", "ついに", "Cuối cùng", ""},
                new String[]{"もちろん", "もちろん", "Tất nhiên", ""},
                new String[]{"やはり", "やはり", "Quả nhiên", ""},
                new String[]{"きっと", "きっと", "Nhất định", ""},
                new String[]{"ぜひ", "ぜひ", "Bằng mọi giá", ""},
                new String[]{"なるべく", "なるべく", "Cố gắng hết sức", ""},
                new String[]{"案外", "あんがい", "Không ngờ đến", "Án Ngoại"},
                new String[]{"もしかすると", "もしかすると", "Có lẽ", ""},
                new String[]{"まさか", "まさか", "Chắc chắn không", ""},
                new String[]{"うっかり", "うっかり", "Lơ đễnh, chểnh mảng", ""},
                new String[]{"つい", "つい", "Vô ý, lỡ", ""},
                new String[]{"思わず", "おもわず", "Bất giác", "Tư"},
                new String[]{"ほっと", "ほっと", "Thở phào nhẹ nhõm", ""}
        );

        for (String[] data : vocabData4) {
            Vocabulary voc = Vocabulary.builder()
                    .unit(unit4)
                    .kanji(data[0])
                    .hiragana(data[1])
                    .romaji(data[1]) 
                    .meaning(data[2])
                    .sinoVietnamese(data[3])
                    .build();
            vocabularyRepository.save(voc);
        }

        System.out.println("====== Cleared old and Seeded Unit 4 with 82 vocabularies ======");

        // Seed Questions
        questionRepository.deleteAll();
        seedQuestionsForUnit(unit1);
        seedQuestionsForUnit(unit2);
        seedQuestionsForUnit(unit3);
        seedQuestionsForUnit(unit4);
        System.out.println("====== Seeded Questions for Units 1, 2, 3, 4 ======");
        } // Close if (vocabularyRepository.count() == 0)

    }

    private void seedQuestionsForUnit(Unit unit) {
        List<Vocabulary> vocabularies = vocabularyRepository.findByUnitId(unit.getId());
        if (vocabularies.size() < 4) return;

        java.util.Random random = new java.util.Random();

        for (int i = 0; i < vocabularies.size(); i++) {
            Vocabulary current = vocabularies.get(i);
            
            // Get 3 random wrong options
            List<Vocabulary> wrongOptions = new java.util.ArrayList<>(vocabularies);
            wrongOptions.remove(i);
            java.util.Collections.shuffle(wrongOptions);
            wrongOptions = wrongOptions.subList(0, 3);

            // Create 4 options
            List<String> options = new java.util.ArrayList<>();
            String correctText = current.getKanji() != null && !current.getKanji().isEmpty() 
                ? current.getKanji() + "（" + current.getHiragana() + "）" 
                : current.getHiragana();
            options.add(correctText);

            for (Vocabulary wrong : wrongOptions) {
                String wrongText = wrong.getKanji() != null && !wrong.getKanji().isEmpty() 
                    ? wrong.getKanji() + "（" + wrong.getHiragana() + "）" 
                    : wrong.getHiragana();
                options.add(wrongText);
            }

            // Shuffle the 4 options
            java.util.Collections.shuffle(options);

            // Find the correct answer ID (A, B, C, or D)
            String correctAnswer = "";
            if (options.get(0).equals(correctText)) correctAnswer = "A";
            else if (options.get(1).equals(correctText)) correctAnswer = "B";
            else if (options.get(2).equals(correctText)) correctAnswer = "C";
            else if (options.get(3).equals(correctText)) correctAnswer = "D";

            Question q = Question.builder()
                    .unit(unit)
                    .vocabulary(current)
                    .questionType(com.example.backend.entity.enums.QuestionType.MEANING_TO_KANJI)
                    .questionText(current.getMeaning())
                    .optionA(options.get(0))
                    .optionB(options.get(1))
                    .optionC(options.get(2))
                    .optionD(options.get(3))
                    .correctAnswer(correctAnswer)
                    .build();

            questionRepository.save(q);
        }
    }
}
