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
                    .title("Unit 1: Con ngÆ°á»i & Cuá»™c sá»‘ng")
                    .description("40 tá»« vá»±ng N3 vá» chá»§ Ä‘á» con ngÆ°á»i, má»‘i quan há»‡ vÃ  cÃ¡c sá»± kiá»‡n trong Ä‘á»i sá»‘ng.")
                    .orderIndex(1)
                    .imageUrl("/assets/hikari_logo.png").level("N3").build();
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
                new String[]{"ç”·æ€§", "ã ã‚“ã›ã„", "Nam giá»›i", "Nam TÃ­nh"},
                new String[]{"å¥³æ€§", "ã˜ã‚‡ã›ã„", "Ná»¯ giá»›i", "Ná»¯ TÃ­nh"},
                new String[]{"é«˜é½¢", "ã“ã†ã‚Œã„", "Tuá»•i cao, cao tuá»•i", "Cao Linh"},
                new String[]{"å¹´ä¸Š", "ã¨ã—ã†ãˆ", "Lá»›n tuá»•i hÆ¡n", "NiÃªn ThÆ°á»£ng"},
                new String[]{"ç›®ä¸Š", "ã‚ã†ãˆ", "Cáº¥p trÃªn, bá» trÃªn", "Má»¥c ThÆ°á»£ng"},
                new String[]{"å…ˆè¼©", "ã›ã‚“ã±ã„", "Tiá»n bá»‘i, ngÆ°á»i Ä‘i trÆ°á»›c", "TiÃªn Bá»‘i"},
                new String[]{"å¾Œè¼©", "ã“ã†ã¯ã„", "Háº­u bá»‘i, ngÆ°á»i Ä‘i sau", "Háº­u Bá»‘i"},
                new String[]{"ä¸Šå¸", "ã˜ã‚‡ã†ã—", "Cáº¥p trÃªn, sáº¿p", "ThÆ°á»£ng TÆ°"},
                new String[]{"ç›¸æ‰‹", "ã‚ã„ã¦", "Äá»‘i phÆ°Æ¡ng, Ä‘á»‘i tÃ¡c", "TÆ°Æ¡ng Thá»§"},
                new String[]{"çŸ¥ã‚Šåˆã„", "ã—ã‚Šã‚ã„", "NgÆ°á»i quen", "Tri Há»£p"},
                new String[]{"å‹äºº", "ã‚†ã†ã˜ã‚“", "Báº¡n bÃ¨", "Há»¯u NhÃ¢n"},
                new String[]{"ä»²", "ãªã‹", "Má»‘i quan há»‡", "Trá»ng"},
                new String[]{"ç”Ÿå¹´æœˆæ—¥", "ã›ã„ã­ã‚“ãŒã£ã´", "NgÃ y thÃ¡ng nÄƒm sinh", "Sinh NiÃªn Nguyá»‡t Nháº­t"},
                new String[]{"èª•ç”Ÿ", "ãŸã‚“ã˜ã‚‡ã†", "Sá»± ra Ä‘á»i", "Äáº£n Sinh"},
                new String[]{"å¹´", "ã¨ã—", "NÄƒm, tuá»•i", "NiÃªn"},
                new String[]{"å‡ºèº«", "ã—ã‚…ã£ã—ã‚“", "Xuáº¥t thÃ¢n, quÃª quÃ¡n", "Xuáº¥t ThÃ¢n"},
                new String[]{"æ•…éƒ·", "ã“ãã‚‡ã†", "QuÃª hÆ°Æ¡ng", "Cá»‘ HÆ°Æ¡ng"},
                new String[]{"æˆé•·", "ã›ã„ã¡ã‚‡ã†", "TrÆ°á»Ÿng thÃ nh, khÃ´n lá»›n", "ThÃ nh TrÆ°á»Ÿng"},
                new String[]{"æˆäºº", "ã›ã„ã˜ã‚“", "NgÆ°á»i trÆ°á»Ÿng thÃ nh", "ThÃ nh NhÃ¢n"},
                new String[]{"åˆæ ¼", "ã”ã†ã‹ã", "Thi Ä‘á»—, trÃºng tuyá»ƒn", "Há»£p CÃ¡ch"},
                new String[]{"é€²å­¦", "ã—ã‚“ãŒã", "Há»c lÃªn cao hÆ¡n", "Tiáº¿n Há»c"},
                new String[]{"é€€å­¦", "ãŸã„ãŒã", "Bá» há»c, thÃ´i há»c", "ThoÃ¡i Há»c"},
                new String[]{"å°±è·", "ã—ã‚…ã†ã—ã‚‡ã", "TÃ¬m viá»‡c, nháº­m chá»©c", "Tá»±u Chá»©c"},
                new String[]{"é€€è·", "ãŸã„ã—ã‚‡ã", "Nghá»‰ viá»‡c, tá»« chá»©c", "ThoÃ¡i Chá»©c"},
                new String[]{"å¤±æ¥­", "ã—ã¤ãŽã‚‡ã†", "Tháº¥t nghiá»‡p", "Tháº¥t Nghiá»‡p"},
                new String[]{"æ®‹æ¥­", "ã–ã‚“ãŽã‚‡ã†", "LÃ m thÃªm giá»", "TÃ n Nghiá»‡p"},
                new String[]{"ç”Ÿæ´»", "ã›ã„ã‹ã¤", "Sinh hoáº¡t, Ä‘á»i sá»‘ng", "Sinh Hoáº¡t"},
                new String[]{"é€šå‹¤", "ã¤ã†ãã‚“", "Äi lÃ m", "ThÃ´ng Cáº§n"},
                new String[]{"å­¦æ­´", "ãŒãã‚Œã", "Báº±ng cáº¥p, quÃ¡ trÃ¬nh há»c táº­p", "Há»c Lá»‹ch"},
                new String[]{"çµ¦æ–™", "ãã‚…ã†ã‚Šã‚‡ã†", "Tiá»n lÆ°Æ¡ng", "Cáº¥p Liá»‡u"},
                new String[]{"é¢æŽ¥", "ã‚ã‚“ã›ã¤", "Phá»ng váº¥n", "Diá»‡n Tiáº¿p"},
                new String[]{"ä¼‘æ†©", "ãã‚…ã†ã‘ã„", "Nghá»‰ giáº£i lao", "HÆ°u Kháº¿"},
                new String[]{"è¦³å…‰", "ã‹ã‚“ã“ã†", "Tham quan, du lá»‹ch", "Quan Quang"},
                new String[]{"å¸°å›½", "ãã“ã", "Vá» nÆ°á»›c", "Quy Quá»‘c"},
                new String[]{"å¸°çœ", "ãã›ã„", "Vá» quÃª", "Quy Tá»‰nh"},
                new String[]{"å¸°å®…", "ããŸã", "Vá» nhÃ ", "Quy Tráº¡ch"},
                new String[]{"å‚åŠ ", "ã•ã‚“ã‹", "Tham gia", "Tham Gia"},
                new String[]{"å‡ºå¸­", "ã—ã‚…ã£ã›ã", "CÃ³ máº·t, tham dá»±", "Xuáº¥t Tá»‹ch"},
                new String[]{"æ¬ å¸­", "ã‘ã£ã›ã", "Váº¯ng máº·t", "Khiáº¿m Tá»‹ch"},
                new String[]{"é…åˆ»", "ã¡ã“ã", "Äáº¿n muá»™n, trá»… giá»", "TrÃ¬ Kháº¯c"},
                new String[]{"åŒ–ç²§", "ã‘ã—ã‚‡ã†", "Trang Ä‘iá»ƒm", "HÃ³a Trang"},
                new String[]{"è¨ˆç®—", "ã‘ã„ã•ã‚“", "TÃ­nh toÃ¡n", "Káº¿ ToÃ¡n"},
                new String[]{"è¨ˆç”»", "ã‘ã„ã‹ã", "Káº¿ hoáº¡ch", "Káº¿ Hoáº¡ch"},
                new String[]{"æˆåŠŸ", "ã›ã„ã“ã†", "ThÃ nh cÃ´ng", "ThÃ nh CÃ´ng"},
                new String[]{"å¤±æ•—", "ã—ã£ã±ã„", "Tháº¥t báº¡i", "Tháº¥t Báº¡i"},
                new String[]{"æº–å‚™", "ã˜ã‚…ã‚“ã³", "Chuáº©n bá»‹", "Chuáº©n Bá»‹"},
                new String[]{"æ•´ç†", "ã›ã„ã‚Š", "Chá»‰nh lÃ½, sáº¯p xáº¿p", "Chá»‰nh LÃ½"},
                new String[]{"æ³¨æ–‡", "ã¡ã‚…ã†ã‚‚ã‚“", "Äáº·t hÃ ng, gá»i mÃ³n", "ChÃº VÄƒn"},
                new String[]{"è²¯é‡‘", "ã¡ã‚‡ãã‚“", "Tiáº¿t kiá»‡m tiá»n", "Trá»¯ Kim"},
                new String[]{"å¾¹å¤œ", "ã¦ã¤ã‚„", "Thá»©c tráº¯ng Ä‘Ãªm", "Triá»‡t Dáº¡"},
                new String[]{"å¼•ã£è¶Šã—", "ã²ã£ã“ã—", "Chuyá»ƒn nhÃ ", "Dáº«n Viá»‡t"},
                new String[]{"èº«é•·", "ã—ã‚“ã¡ã‚‡ã†", "Chiá»u cao", "ThÃ¢n TrÆ°á»ng"},
                new String[]{"ä½“é‡", "ãŸã„ã˜ã‚…ã†", "CÃ¢n náº·ng", "Thá»ƒ Trá»ng"},
                new String[]{"ã‘ãŒ", "ã‘ãŒ", "Váº¿t thÆ°Æ¡ng, cháº¥n thÆ°Æ¡ng", "(QuÃ¡i NgÃ£)"},
                new String[]{"ä¼š", "ã‹ã„", "Tiá»‡c, há»™i", "Há»™i"},
                new String[]{"è¶£å‘³", "ã—ã‚…ã¿", "Sá»Ÿ thÃ­ch", "ThÃº Vá»‹"},
                new String[]{"èˆˆå‘³", "ãã‚‡ã†ã¿", "Há»©ng thÃº, quan tÃ¢m", "HÆ°ng Vá»‹"},
                new String[]{"æ€ã„å‡º", "ãŠã‚‚ã„ã§", "Ká»· niá»‡m", "TÆ° Xuáº¥t"},
                new String[]{"å†—è«‡", "ã˜ã‚‡ã†ã ã‚“", "NÃ³i Ä‘Ã¹a", "NhÅ©ng ÄÃ m"},
                new String[]{"ç›®çš„", "ã‚‚ãã¦ã", "Má»¥c Ä‘Ã­ch", "Má»¥c ÄÃ­ch"},
                new String[]{"ç´„æŸ", "ã‚„ããã", "Lá»i há»©a, cuá»™c háº¹n", "Æ¯á»›c ThÃºc"},
                new String[]{"ãŠã—ã‚ƒã¹ã‚Š", "ãŠã—ã‚ƒã¹ã‚Š", "NÃ³i chuyá»‡n, ngÆ°á»i hay nÃ³i", "-"},
                new String[]{"é æ…®", "ãˆã‚“ã‚Šã‚‡", "Ngáº§n ngáº¡i, khÃ¡ch sÃ¡o", "Viá»…n Lá»±"},
                new String[]{"æˆ‘æ…¢", "ãŒã¾ã‚“", "Chá»‹u Ä‘á»±ng, nháº«n nhá»‹n", "NgÃ£ Máº¡n"},
                new String[]{"è¿·æƒ‘", "ã‚ã„ã‚ã", "Phiá»n toÃ¡i, lÃ m phiá»n", "MÃª Hoáº·c"},
                new String[]{"å¸Œæœ›", "ãã¼ã†", "Hy vá»ng, mong muá»‘n", "Hy Vá»ng"},
                new String[]{"å¤¢", "ã‚†ã‚", "Giáº¥c mÆ¡, Æ°á»›c mÆ¡", "Má»™ng"},
                new String[]{"è³›æˆ", "ã•ã‚“ã›ã„", "TÃ¡n thÃ nh, Ä‘á»“ng Ã½", "TÃ¡n ThÃ nh"},
                new String[]{"åå¯¾", "ã¯ã‚“ãŸã„", "Pháº£n Ä‘á»‘i, ngÆ°á»£c láº¡i", "Pháº£n Äá»‘i"},
                new String[]{"æƒ³åƒ", "ãã†ãžã†", "TÆ°á»Ÿng tÆ°á»£ng", "TÆ°á»Ÿng TÆ°á»£ng"},
                new String[]{"åŠªåŠ›", "ã©ã‚Šã‚‡ã", "Ná»— lá»±c, cá»‘ gáº¯ng", "Ná»— Lá»±c"},
                new String[]{"å¤ªé™½", "ãŸã„ã‚ˆã†", "Máº·t trá»i", "ThÃ¡i DÆ°Æ¡ng"},
                new String[]{"åœ°çƒ", "ã¡ãã‚…ã†", "TrÃ¡i Ä‘áº¥t", "Äá»‹a Cáº§u"},
                new String[]{"æ¸©åº¦", "ãŠã‚“ã©", "Nhiá»‡t Ä‘á»™", "Ã”n Äá»™"},
                new String[]{"æ¹¿åº¦", "ã—ã¤ã©", "Äá»™ áº©m", "Tháº¥p Äá»™"},
                new String[]{"æ¹¿æ°—", "ã—ã£ã‘", "HÆ¡i áº©m", "Tháº¥p KhÃ­"},
                new String[]{"æ¢…é›¨", "ã¤ã‚†", "MÃ¹a mÆ°a", "Mai VÅ©"},
                new String[]{"ã‹ã³", "ã‹ã³", "Náº¥m má»‘c", "-"},
                new String[]{"æš–æˆ¿", "ã ã‚“ã¼ã†", "LÃ² sÆ°á»Ÿi, mÃ¡y sÆ°á»Ÿi", "NoÃ£n PhÃ²ng"},
                new String[]{"çš®", "ã‹ã‚", "Da, vá»", "BÃ¬"},
                new String[]{"ç¼¶", "ã‹ã‚“", "Lon, há»™p kim loáº¡i", "Phá»¯u / Can"},
                new String[]{"ç”»é¢", "ãŒã‚ã‚“", "MÃ n hÃ¬nh", "Há»a Diá»‡n"},
                new String[]{"ç•ªçµ„", "ã°ã‚“ãã¿", "ChÆ°Æ¡ng trÃ¬nh (TV, radio)", "PhiÃªn Tá»•"},
                new String[]{"è¨˜äº‹", "ãã˜", "KÃ½ sá»±, bÃ i bÃ¡o", "KÃ½ Sá»±"},
                new String[]{"è¿‘æ‰€", "ãã‚“ã˜ã‚‡", "HÃ ng xÃ³m, lÃ¢n cáº­n", "Cáº­n Sá»Ÿ"},
                new String[]{"è­¦å¯Ÿ", "ã‘ã„ã•ã¤", "Cáº£nh sÃ¡t", "Cáº£nh SÃ¡t"},
                new String[]{"çŠ¯äºº", "ã¯ã‚“ã«ã‚“", "Tá»™i pháº¡m, thá»§ pháº¡m", "Pháº¡m NhÃ¢n"},
                new String[]{"å°éŠ­", "ã“ãœã«", "Tiá»n láº»", "Tiá»ƒu Tiá»n"},
                new String[]{"ã”ã¡ãã†", "ã”ã¡ãã†", "Khao, bá»¯a Äƒn ngon", "(Ngá»± TrÃ¬ Táº©u)"},
                new String[]{"ä½œè€…", "ã•ãã—ã‚ƒ", "TÃ¡c giáº£", "TÃ¡c Giáº£"},
                new String[]{"ä½œå“", "ã•ãã²ã‚“", "TÃ¡c pháº©m", "TÃ¡c Pháº©m"},
                new String[]{"åˆ¶æœ", "ã›ã„ãµã", "Äá»“ng phá»¥c", "Cháº¿ Phá»¥c"},
                new String[]{"æ´—å‰¤", "ã›ã‚“ã–ã„", "Cháº¥t táº©y rá»­a", "Táº©y Tá»…"},
                new String[]{"åº•", "ãã“", "ÄÃ¡y", "Äá»ƒ"},
                new String[]{"åœ°ä¸‹", "ã¡ã‹", "Táº§ng háº§m, dÆ°á»›i Ä‘áº¥t", "Äá»‹a Háº¡"},
                new String[]{"å¯º", "ã¦ã‚‰", "ChÃ¹a", "Tá»±"},
                new String[]{"é“è·¯", "ã©ã†ã‚", "ÄÆ°á»ng bá»™", "Äáº¡o Lá»™"},
                new String[]{"å‚", "ã•ã‹", "Dá»‘c", "Pháº£n"},
                new String[]{"ç…™", "ã‘ã‚€ã‚Š", "KhÃ³i", "YÃªn"},
                new String[]{"ç°", "ã¯ã„", "TÃ n, tro", "KhÃ´i"},
                new String[]{"åˆ¤", "ã¯ã‚“", "Con dáº¥u", "PhÃ¡n"},
                new String[]{"ååˆº", "ã‚ã„ã—", "Danh thiáº¿p", "Danh Thá»©"},
                new String[]{"å…è¨±", "ã‚ã‚“ãã‚‡", "Giáº¥y phÃ©p", "Miá»…n Há»©a"},
                new String[]{"å¤šã", "ãŠãŠã", "Nhiá»u", "Äa"},
                new String[]{"å‰åŠ", "ãœã‚“ã¯ã‚“", "Ná»­a Ä‘áº§u", "Tiá»n BÃ¡n"},
                new String[]{"å¾ŒåŠ", "ã“ã†ã¯ã‚“", "Ná»­a sau", "Háº­u BÃ¡n"},
                new String[]{"æœ€é«˜", "ã•ã„ã“ã†", "Cao nháº¥t, tuyá»‡t vá»i nháº¥t", "Tá»‘i Cao"},
                new String[]{"æœ€ä½Ž", "ã•ã„ã¦ã„", "Tháº¥p nháº¥t, tá»“i tá»‡ nháº¥t", "Tá»‘i ÄÃª"},
                new String[]{"æœ€åˆ", "ã•ã„ã—ã‚‡", "Äáº§u tiÃªn", "Tá»‘i SÆ¡"},
                new String[]{"æœ€å¾Œ", "ã•ã„ã”", "Cuá»‘i cÃ¹ng", "Tá»‘i Háº­u"},
                new String[]{"è‡ªå‹•", "ã˜ã©ã†", "Tá»± Ä‘á»™ng", "Tá»± Äá»™ng"},
                new String[]{"ç¨®é¡ž", "ã—ã‚…ã‚‹ã„", "Chá»§ng loáº¡i, loáº¡i", "Chá»§ng Loáº¡i"},
                new String[]{"æ€§æ ¼", "ã›ã„ã‹ã", "TÃ­nh cÃ¡ch", "TÃ­nh CÃ¡ch"},
                new String[]{"æ€§è³ª", "ã›ã„ã—ã¤", "TÃ­nh cháº¥t", "TÃ­nh Cháº¥t"},
                new String[]{"é †ç•ª", "ã˜ã‚…ã‚“ã°ã‚“", "Thá»© tá»±, láº§n lÆ°á»£t", "Thuáº­n PhiÃªn"},
                new String[]{"ç•ª", "ã°ã‚“", "LÆ°á»£t, sá»‘", "PhiÃªn"},
                new String[]{"æ–¹æ³•", "ã»ã†ã»ã†", "PhÆ°Æ¡ng phÃ¡p", "PhÆ°Æ¡ng PhÃ¡p"},
                new String[]{"è£½å“", "ã›ã„ã²ã‚“", "Sáº£n pháº©m", "Cháº¿ Pháº©m"},
                new String[]{"å€¤ä¸ŠãŒã‚Š", "ã­ã‚ãŒã‚Š", "TÄƒng giÃ¡", "Trá»‹ ThÆ°á»£ng"},
                new String[]{"ç”Ÿ", "ãªã¾", "TÆ°Æ¡i sá»‘ng, nguyÃªn cháº¥t", "Sinh"}
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
                    .title("Unit 2: Äá»™ng tá»« 1")
                    .description("100 tá»« vá»±ng N3 vá» chá»§ Ä‘á» Ä‘á»™ng tá»« pháº§n 1.")
                    .orderIndex(2)
                    .imageUrl("/assets/hikari_logo.png").level("N3").build();
            unitRepository.save(unit2);
        } else {
            unit2 = unitRepository.findAll().get(1);
        }

        List<String[]> vocabData2 = Arrays.asList(
                new String[]{"æ¸‡ã", "ã‹ã‚ã", "KhÃ¡t nÆ°á»›c", "KhÃ¡t"},
                new String[]{"å—…ã", "ã‹ã", "Ngá»­i", "Khá»©u"},
                new String[]{"å©ã", "ãŸãŸã", "ÄÃ¡nh, vá»—, gÃµ", "Kháº¥u"},
                new String[]{"æ®´ã‚‹", "ãªãã‚‹", "Äáº¥m", "áº¨u"},
                new String[]{"ã‘ã‚‹", "ã‘ã‚‹", "ÄÃ¡", ""},
                new String[]{"æŠ±ã", "ã ã", "Ã”m, áºµm", "BÃ£o"},
                new String[]{"å€’ã‚Œã‚‹", "ãŸãŠã‚Œã‚‹", "NgÃ£, Ä‘á»•, bá»‡nh", "Äáº£o"},
                new String[]{"å€’ã™", "ãŸãŠã™", "LÃ m Ä‘á»•, Ä‘Ã¡nh báº¡i", "Äáº£o"},
                new String[]{"èµ·ãã‚‹ãƒ»èµ·ã“ã‚‹", "ãŠãã‚‹ãƒ»ãŠã“ã‚‹", "Thá»©c dáº­y, xáº£y ra", "Khá»Ÿi"},
                new String[]{"èµ·ã“ã™", "ãŠã“ã™", "ÄÃ¡nh thá»©c, gÃ¢y ra", "Khá»Ÿi"},
                new String[]{"å°‹ã­ã‚‹", "ãŸãšã­ã‚‹", "Há»i", "Táº§m"},
                new String[]{"å‘¼ã¶", "ã‚ˆã¶", "Gá»i", "HÃ´"},
                new String[]{"å«ã¶", "ã•ã‘ã¶", "KÃªu, la hÃ©t", "Khiáº¿u"},
                new String[]{"é»™ã‚‹", "ã ã¾ã‚‹", "Im láº·ng", "Máº·c"},
                new String[]{"é£¼ã†", "ã‹ã†", "NuÃ´i (Ä‘á»™ng váº­t)", "Tá»±"},
                new String[]{"æ•°ãˆã‚‹", "ã‹ãžãˆã‚‹", "Äáº¿m", "Sá»‘"},
                new String[]{"ä¹¾ã", "ã‹ã‚ã", "KhÃ´", "Can"},
                new String[]{"ä¹¾ã‹ã™", "ã‹ã‚ã‹ã™", "LÃ m khÃ´, sáº¥y khÃ´", "Can"},
                new String[]{"ç•³ã‚€", "ãŸãŸã‚€", "Gáº¥p, xáº¿p", "Äiá»‡p"},
                new String[]{"èª˜ã†", "ã•ãã†", "Má»i, rá»§ rÃª", "Dá»¥"},
                new String[]{"ãŠã”ã‚‹", "ãŠã”ã‚‹", "Khao, chiÃªu Ä‘Ã£i", ""},
                new String[]{"é ã‹ã‚‹", "ã‚ãšã‹ã‚‹", "TrÃ´ng nom, chÄƒm sÃ³c", "Dá»±"},
                new String[]{"é ã‘ã‚‹", "ã‚ãšã‘ã‚‹", "Gá»­i gáº¯m, giao phÃ³", "Dá»±"},
                new String[]{"æ±ºã¾ã‚‹", "ãã¾ã‚‹", "ÄÆ°á»£c quyáº¿t Ä‘á»‹nh", "Quyáº¿t"},
                new String[]{"æ±ºã‚ã‚‹", "ãã‚ã‚‹", "Quyáº¿t Ä‘á»‹nh", "Quyáº¿t"},
                new String[]{"å†™ã‚‹", "ã†ã¤ã‚‹", "ÄÆ°á»£c chá»¥p, chiáº¿u", "Táº£"},
                new String[]{"å†™ã™", "ã†ã¤ã™", "Chá»¥p, sao chÃ©p", "Táº£"},
                new String[]{"æ€ã„å‡ºã™", "ãŠã‚‚ã„ã ã™", "Nhá»› láº¡i", "TÆ° Xuáº¥t"},
                new String[]{"æ•™ã‚ã‚‹", "ãŠãã‚ã‚‹", "ÄÆ°á»£c dáº¡y, há»c Ä‘Æ°á»£c", "GiÃ¡o"},
                new String[]{"ç”³ã—è¾¼ã‚€", "ã‚‚ã†ã—ã“ã‚€", "ÄÄƒng kÃ½", "ThÃ¢n VÃ o"},
                new String[]{"æ–­ã‚‹", "ã“ã¨ã‚ã‚‹", "Tá»« chá»‘i", "Äoáº¡n"},
                new String[]{"è¦‹ã¤ã‹ã‚‹", "ã¿ã¤ã‹ã‚‹", "ÄÆ°á»£c tÃ¬m tháº¥y", "Kiáº¿n"},
                new String[]{"è¦‹ã¤ã‘ã‚‹", "ã¿ã¤ã‘ã‚‹", "TÃ¬m tháº¥y", "Kiáº¿n"},
                new String[]{"æ•ã¾ã‚‹", "ã¤ã‹ã¾ã‚‹", "Bá»‹ báº¯t", "Bá»™"},
                new String[]{"æ•ã¾ãˆã‚‹", "ã¤ã‹ã¾ãˆã‚‹", "Báº¯t, tÃ³m", "Bá»™"},
                new String[]{"ä¹—ã‚‹", "ã®ã‚‹", "LÃªn xe, cá»¡i", "Thá»«a"},
                new String[]{"ä¹—ã›ã‚‹", "ã®ã›ã‚‹", "Cho lÃªn xe, cháº¥t lÃªn", "Thá»«a"},
                new String[]{"é™ã‚Šã‚‹ãƒ»ä¸‹ã‚Šã‚‹", "ãŠã‚Šã‚‹", "Xuá»‘ng (xe, nÃºi)", "GiÃ¡ng / Háº¡"},
                new String[]{"é™ã‚ã™ãƒ»ä¸‹ã‚ã™", "ãŠã‚ã™", "Cho xuá»‘ng, háº¡ xuá»‘ng", "GiÃ¡ng / Háº¡"},
                new String[]{"ç›´ã‚‹", "ãªãŠã‚‹", "ÄÆ°á»£c sá»­a chá»¯a", "Trá»±c"},
                new String[]{"ç›´ã™", "ãªãŠã™", "Sá»­a chá»¯a", "Trá»±c"},
                new String[]{"æ²»ã‚‹", "ãªãŠã‚‹", "Khá»i (bá»‡nh)", "Trá»‹"},
                new String[]{"æ²»ã™", "ãªãŠã™", "Chá»¯a (bá»‡nh)", "Trá»‹"},
                new String[]{"äº¡ããªã‚‹", "ãªããªã‚‹", "Máº¥t, qua Ä‘á»i", "Vong"},
                new String[]{"äº¡ãã™", "ãªãã™", "Máº¥t (ngÆ°á»i thÃ¢n)", "Vong"},
                new String[]{"ç”Ÿã¾ã‚Œã‚‹", "ã†ã¾ã‚Œã‚‹", "ÄÆ°á»£c sinh ra", "Sinh"},
                new String[]{"ç”Ÿã‚€ãƒ»ç”£ã‚€", "ã†ã‚€", "Sinh, Ä‘áº»", "Sinh / Sáº£n"},
                new String[]{"å‡ºä¼šã†", "ã§ã‚ã†", "Gáº·p gá»¡ (tÃ¬nh cá»)", "Xuáº¥t Há»™i"},
                new String[]{"è¨ªã­ã‚‹", "ãŸãšã­ã‚‹", "Äáº¿n thÄƒm", "Phá»ng"},
                new String[]{"ä»˜ãåˆã†", "ã¤ãã‚ã†", "Háº¹n hÃ², giao du", "PhÃ³ Há»£p"},
                new String[]{"åŠ¹ã", "ãã", "CÃ³ hiá»‡u quáº£", "Hiá»‡u"},
                new String[]{"ã¯ã‚„ã‚‹", "ã¯ã‚„ã‚‹", "Phá»• biáº¿n, thá»‹nh hÃ nh", ""},
                new String[]{"çµŒã¤", "ãŸã¤", "TrÃ´i qua (thá»i gian)", "Kinh"},
                new String[]{"é–“ã«åˆã†", "ã¾ã«ã‚ã†", "Ká»‹p thá»i gian", "Gian Há»£p"},
                new String[]{"é–“ã«åˆã‚ã›ã‚‹", "ã¾ã«ã‚ã‚ã›ã‚‹", "LÃ m cho ká»‹p", "Gian Há»£p"},
                new String[]{"é€šã†", "ã‹ã‚ˆã†", "Äi láº¡i thÆ°á»ng xuyÃªn", "ThÃ´ng"},
                new String[]{"è¾¼ã‚€", "ã“ã‚€", "ÄÃ´ng Ä‘Ãºc", "VÃ o"},
                new String[]{"ã™ã‚Œé•ã†", "ã™ã‚Œã¡ãŒã†", "Äi lÆ°á»›t qua nhau", "Vi"},
                new String[]{"æŽ›ã‹ã‚‹", "ã‹ã‹ã‚‹", "Tá»‘n (thá»i gian, tiá»n), treo", "Quáº£i"},
                new String[]{"æŽ›ã‘ã‚‹", "ã‹ã‘ã‚‹", "Treo, gá»i (Ä‘iá»‡n thoáº¡i)", "Quáº£i"},
                new String[]{"å‹•ã", "ã†ã”ã", "Di chuyá»ƒn, hoáº¡t Ä‘á»™ng", "Äá»™ng"},
                new String[]{"å‹•ã‹ã™", "ã†ã”ã‹ã™", "LÃ m chuyá»ƒn Ä‘á»™ng", "Äá»™ng"},
                new String[]{"é›¢ã‚Œã‚‹", "ã¯ãªã‚Œã‚‹", "CÃ¡ch xa, rá»i xa", "Ly"},
                new String[]{"é›¢ã™", "ã¯ãªã™", "TÃ¡ch ra, buÃ´ng ra", "Ly"},
                new String[]{"ã¶ã¤ã‹ã‚‹", "ã¶ã¤ã‹ã‚‹", "Va cháº¡m, Ä‘á»¥ng", ""},
                new String[]{"ã¶ã¤ã‘ã‚‹", "ã¶ã¤ã‘ã‚‹", "ÄÃ¢m vÃ o, hÃºc vÃ o", ""},
                new String[]{"ã“ã¼ã‚Œã‚‹", "ã“ã¼ã‚Œã‚‹", "Bá»‹ trÃ n, Ä‘á»• ra", ""},
                new String[]{"ã“ã¼ã™", "ã“ã¼ã™", "LÃ m trÃ n, lÃ m Ä‘á»•", ""},
                new String[]{"ãµã", "ãµã", "Lau, chÃ¹i", ""},
                new String[]{"ç‰‡ä»˜ã", "ã‹ãŸã¥ã", "ÄÆ°á»£c dá»n dáº¹p", "Phiáº¿n PhÃ³"},
                new String[]{"ç‰‡ä»˜ã‘ã‚‹", "ã‹ãŸã¥ã‘ã‚‹", "Dá»n dáº¹p", "Phiáº¿n PhÃ³"},
                new String[]{"åŒ…ã‚€", "ã¤ã¤ã‚€", "GÃ³i, bá»c", "Bao"},
                new String[]{"å¼µã‚‹", "ã¯ã‚‹", "DÃ¡n, cÄƒng ra", "TrÆ°Æ¡ng"},
                new String[]{"ç„¡ããªã‚‹", "ãªããªã‚‹", "Bá»‹ máº¥t, háº¿t", "VÃ´"},
                new String[]{"ç„¡ãã™", "ãªãã™", "LÃ m máº¥t", "VÃ´"},
                new String[]{"è¶³ã‚Šã‚‹", "ãŸã‚Šã‚‹", "Äá»§", "TÃºc"},
                new String[]{"æ®‹ã‚‹", "ã®ã“ã‚‹", "CÃ²n láº¡i", "TÃ n"},
                new String[]{"æ®‹ã™", "ã®ã“ã™", "Bá» láº¡i, chá»«a láº¡i", "TÃ n"},
                new String[]{"è…ã‚‹", "ãã•ã‚‹", "Thiu, thá»‘i, má»¥c nÃ¡t", "Há»§"},
                new String[]{"ã‚€ã‘ã‚‹", "ã‚€ã‘ã‚‹", "Bá»‹ lá»™t, bong trÃ³c", ""},
                new String[]{"ã‚€ã", "ã‚€ã", "BÃ³c, lá»™t (vá»)", ""},
                new String[]{"æ»‘ã‚‹", "ã™ã¹ã‚‹", "TrÆ°á»£t", "Hoáº¡t"},
                new String[]{"ç©ã‚‚ã‚‹", "ã¤ã‚‚ã‚‹", "TÃ­ch tá»¥, cháº¥t Ä‘á»‘ng", "TÃ­ch"},
                new String[]{"ç©ã‚€", "ã¤ã‚€", "Cháº¥t lÃªn, tÃ­ch lÅ©y", "TÃ­ch"},
                new String[]{"ç©ºã", "ã‚ã", "Trá»‘ng, rá»—ng, ráº£nh rá»—i", "KhÃ´ng"},
                new String[]{"ç©ºã‘ã‚‹", "ã‚ã‘ã‚‹", "LÃ m trá»‘ng, Ä‘á»¥c lá»—", "KhÃ´ng"},
                new String[]{"ä¸‹ãŒã‚‹", "ã•ãŒã‚‹", "Giáº£m, Ä‘i xuá»‘ng", "Háº¡"},
                new String[]{"ä¸‹ã’ã‚‹", "ã•ã’ã‚‹", "LÃ m giáº£m, háº¡ xuá»‘ng", "Háº¡"},
                new String[]{"å†·ãˆã‚‹", "ã²ãˆã‚‹", "Bá»‹ láº¡nh Ä‘i", "LÃ£nh"},
                new String[]{"å†·ã‚„ã™", "ã²ã‚„ã™", "LÃ m láº¡nh, Æ°á»›p láº¡nh", "LÃ£nh"},
                new String[]{"å†·ã‚ã‚‹", "ã•ã‚ã‚‹", "Nguá»™i Ä‘i", "LÃ£nh"},
                new String[]{"å†·ã¾ã™", "ã•ã¾ã™", "LÃ m nguá»™i", "LÃ£nh"},
                new String[]{"ç‡ƒãˆã‚‹", "ã‚‚ãˆã‚‹", "ChÃ¡y", "NhiÃªn"},
                new String[]{"ç‡ƒã‚„ã™", "ã‚‚ã‚„ã™", "Äá»‘t chÃ¡y", "NhiÃªn"},
                new String[]{"æ²¸ã", "ã‚ã", "SÃ´i lÃªn", "PhÃ­"},
                new String[]{"æ²¸ã‹ã™", "ã‚ã‹ã™", "Äun sÃ´i", "PhÃ­"},
                new String[]{"é³´ã‚‹", "ãªã‚‹", "KÃªu, reo", "Minh"},
                new String[]{"é³´ã‚‰ã™", "ãªã‚‰ã™", "Báº¥m (cÃ²i), lÃ m kÃªu", "Minh"},
                new String[]{"å½¹ç«‹ã¤", "ã‚„ãã ã¤", "CÃ³ Ã­ch, há»¯u Ã­ch", "Dá»‹ch Láº­p"},
                new String[]{"å½¹ç«‹ã¦ã‚‹", "ã‚„ãã ã¦ã‚‹", "á»¨ng dá»¥ng, lÃ m cho cÃ³ Ã­ch", "Dá»‹ch Láº­p"}
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
                    .title("Unit 3: Tá»« loáº¡i khÃ¡c & Danh tá»«")
                    .description("120 tá»« vá»±ng N3 vá» cÃ¡c chá»§ Ä‘á» Ä‘a dáº¡ng.")
                    .orderIndex(3)
                    .imageUrl("/assets/hikari_logo.png").level("N3").build();
            unitRepository.save(unit3);
        } else {
            unit3 = unitRepository.findAll().get(2);
        }

        List<String[]> vocabData3 = Arrays.asList(
                new String[]{"é£¾ã‚Š", "ã‹ã–ã‚Š", "Sá»± trang trÃ­, Ä‘á»“ trang trÃ­", "Sá»©c"},
                new String[]{"éŠã³", "ã‚ãã³", "TrÃ² chÆ¡i, sá»± vui chÆ¡i", "Du"},
                new String[]{"é›†ã¾ã‚Š", "ã‚ã¤ã¾ã‚Š", "Cuá»™c há»p, sá»± tá»¥ táº­p", "Táº­p"},
                new String[]{"æ•™ãˆ", "ãŠã—ãˆ", "Lá»i dáº¡y, giÃ¡o lÃ½", "GiÃ¡o"},
                new String[]{"é ¼ã¿", "ãŸã®ã¿", "Lá»i nhá» váº£, yÃªu cáº§u", "Láº¡i"},
                new String[]{"è‹¦åŠ´", "ãã‚ã†", "Gian khá»•, váº¥t váº£", "Khá»• Lao"},
                new String[]{"ä¸–è©±", "ã›ã‚", "ChÄƒm sÃ³c, giÃºp Ä‘á»¡", "Tháº¿ Thoáº¡i"},
                new String[]{"å¿œæ´", "ãŠã†ãˆã‚“", "Cá»• vÅ©, há»— trá»£", "á»¨ng Viá»‡n"},
                new String[]{"æœŸå¾…", "ããŸã„", "Ká»³ vá»ng, mong Ä‘á»£i", "Ká»³ ÄÃ£i"},
                new String[]{"æ„Ÿè¬", "ã‹ã‚“ã—ã‚ƒ", "Cáº£m táº¡, biáº¿t Æ¡n", "Cáº£m Táº¡"},
                new String[]{"æ‚©ã¿", "ãªã‚„ã¿", "Sá»± phiá»n nÃ£o, trÄƒn trá»Ÿ", "NÃ£o"},
                new String[]{"è¿·ã„", "ã¾ã‚ˆã„", "Sá»± bÄƒn khoÄƒn, láº¡c lá»‘i", "MÃª"},
                new String[]{"é•ã„", "ã¡ãŒã„", "Sá»± khÃ¡c biá»‡t", "Vi"},
                new String[]{"é–“é•ã„", "ã¾ã¡ãŒã„", "Lá»—i láº§m, sai sÃ³t", "Gian Vi"},
                new String[]{"æ€’ã‚Š", "ã„ã‹ã‚Š", "Sá»± tá»©c giáº­n", "Ná»™"},
                new String[]{"ç¥ˆã‚Š", "ã„ã®ã‚Š", "Lá»i cáº§u nguyá»‡n", "Ká»³"},
                new String[]{"ç¥­ã‚Š", "ã¾ã¤ã‚Š", "Lá»… há»™i", "Táº¿"},
                new String[]{"é¡˜ã„", "ã­ãŒã„", "Lá»i thá»‰nh cáº§u, Æ°á»›c nguyá»‡n", "Nguyá»‡n"},
                new String[]{"åŠ©ã‘", "ãŸã™ã‘", "Sá»± giÃºp Ä‘á»¡", "Trá»£"},
                new String[]{"ä¼‘ã¿", "ã‚„ã™ã¿", "Sá»± nghá»‰ ngÆ¡i, ngÃ y nghá»‰", "HÆ°u"},
                new String[]{"è€ƒãˆ", "ã‹ã‚“ãŒãˆ", "Suy nghÄ©, Ã½ tÆ°á»Ÿng", "Kháº£o"},
                new String[]{"æˆ»ã‚Š", "ã‚‚ã©ã‚Š", "Sá»± quay láº¡i, tiá»n tráº£ láº¡i", "Lá»‡"},
                new String[]{"å¤‰ã‚ã‚Š", "ã‹ã‚ã‚Š", "Sá»± thay Ä‘á»•i", "Biáº¿n"},
                new String[]{"åƒã", "ã¯ãŸã‚‰ã", "Chá»©c nÄƒng, hoáº¡t Ä‘á»™ng", "Äá»™ng"},
                new String[]{"çŸ¥ã‚‰ã›", "ã—ã‚‰ã›", "ThÃ´ng bÃ¡o, tin tá»©c", "Tri"},
                new String[]{"å–œã³", "ã‚ˆã‚ã“ã³", "Niá»m vui", "Há»·"},
                new String[]{"ç¬‘ã„", "ã‚ã‚‰ã„", "Tiáº¿ng cÆ°á»i, ná»¥ cÆ°á»i", "Tiáº¿u"},
                new String[]{"é©šã", "ãŠã©ã‚ã", "Sá»± ngáº¡c nhiÃªn", "Kinh"},
                new String[]{"æ‚²ã—ã¿", "ã‹ãªã—ã¿", "Ná»—i buá»“n", "Bi"},
                new String[]{"å¹¸ã›", "ã—ã‚ã‚ã›", "Háº¡nh phÃºc", "Háº¡nh"},
                new String[]{"å¾—æ„", "ã¨ãã„", "Giá»i, tá»± hÃ o", "Äáº¯c Ã"},
                new String[]{"è‹¦æ‰‹", "ã«ãŒã¦", "KÃ©m, yáº¿u", "Khá»• Thá»§"},
                new String[]{"ç†±å¿ƒ", "ã­ã£ã—ã‚“", "Nhiá»‡t tÃ¬nh", "Nhiá»‡t TÃ¢m"},
                new String[]{"å¤¢ä¸­", "ã‚€ã¡ã‚…ã†", "Say sÆ°a, Ä‘am mÃª", "Má»™ng Trung"},
                new String[]{"é€€å±ˆ", "ãŸã„ãã¤", "ChÃ¡n ngáº¯t, táº» nháº¡t", "ThoÃ¡i Khuáº¥t"},
                new String[]{"å¥åº·", "ã‘ã‚“ã“ã†", "Khá»e máº¡nh", "Kiá»‡n Khang"},
                new String[]{"è‹¦ã—ã„", "ãã‚‹ã—ã„", "Äau khá»•, cháº­t váº­t", "Khá»•"},
                new String[]{"å¹³æ°—", "ã¸ã„ã", "BÃ¬nh tháº£n, khÃ´ng sao", "BÃ¬nh KhÃ­"},
                new String[]{"æ‚”ã—ã„", "ãã‚„ã—ã„", "Tiáº¿c nuá»‘i, cay cÃº", "Há»‘i"},
                new String[]{"ç¾¨ã¾ã—ã„", "ã†ã‚‰ã‚„ã¾ã—ã„", "Ghen tá»‹, thÃ¨m muá»‘n", "Tiá»‡n"},
                new String[]{"ã‹ã‚†ã„", "ã‹ã‚†ã„", "Ngá»©a", ""},
                new String[]{"ãŠã¨ãªã—ã„", "ãŠã¨ãªã—ã„", "Hiá»n lÃ nh, tráº§m tÃ­nh", ""},
                new String[]{"æˆ‘æ…¢å¼·ã„", "ãŒã¾ã‚“ã¥ã‚ˆã„", "Giá»i chá»‹u Ä‘á»±ng", "NgÃ£ Máº¡n CÆ°á»ng"},
                new String[]{"æ­£ç›´", "ã—ã‚‡ã†ã˜ã", "Trung thá»±c", "ChÃ­nh Trá»±c"},
                new String[]{"ã‘ã¡", "ã‘ã¡", "Keo kiá»‡t", ""},
                new String[]{"ã‚ãŒã¾ã¾", "ã‚ãŒã¾ã¾", "Ãch ká»·, bÆ°á»›ng bá»‰nh", ""},
                new String[]{"ç©æ¥µçš„", "ã›ã£ãã‚‡ãã¦ã", "CÃ³ tÃ­nh tÃ­ch cá»±c", "TÃ­ch Cá»±c ÄÃ­ch"},
                new String[]{"æ¶ˆæ¥µçš„", "ã—ã‚‡ã†ãã‚‡ãã¦ã", "CÃ³ tÃ­nh tiÃªu cá»±c", "TiÃªu Cá»±c ÄÃ­ch"},
                new String[]{"æº€è¶³", "ã¾ã‚“ãžã", "Thá»a mÃ£n, hÃ i lÃ²ng", "MÃ£n TÃºc"},
                new String[]{"ä¸æº€", "ãµã¾ã‚“", "Báº¥t mÃ£n", "Báº¥t MÃ£n"},
                new String[]{"ä¸å®‰", "ãµã‚ã‚“", "Báº¥t an", "Báº¥t An"},
                new String[]{"å¤§å¤‰", "ãŸã„ã¸ã‚“", "KhÃ³ khÄƒn, váº¥t váº£", "Äáº¡i Biáº¿n"},
                new String[]{"ç„¡ç†", "ã‚€ã‚Š", "QuÃ¡ sá»©c, vÃ´ lÃ½", "VÃ´ LÃ½"},
                new String[]{"ä¸æ³¨æ„", "ãµã¡ã‚…ã†ã„", "Báº¥t cáº©n", "Báº¥t ChÃº Ã"},
                new String[]{"æ¥½", "ã‚‰ã", "NhÃ n nhÃ£, thoáº£i mÃ¡i", "Láº¡c"},
                new String[]{"é¢å€’", "ã‚ã‚“ã©ã†", "Phiá»n phá»©c", "Diá»‡n Äáº£o"},
                new String[]{"å¤±ç¤¼", "ã—ã¤ã‚Œã„", "Tháº¥t lá»…", "Tháº¥t Lá»…"},
                new String[]{"å½“ç„¶", "ã¨ã†ãœã‚“", "ÄÆ°Æ¡ng nhiÃªn", "ÄÆ°Æ¡ng NhiÃªn"},
                new String[]{"æ„å¤–", "ã„ãŒã„", "NgoÃ i dá»± tÃ­nh, báº¥t ngá»", "Ã Ngoáº¡i"},
                new String[]{"çµæ§‹", "ã‘ã£ã“ã†", "KhÃ¡ tá»‘t, Ä‘á»§ rá»“i", "Káº¿t Cáº¥u"},
                new String[]{"æ´¾æ‰‹", "ã¯ã§", "LÃ²e loáº¹t, sáº·c sá»¡", "PhÃ¡i Thá»§"},
                new String[]{"åœ°å‘³", "ã˜ã¿", "Giáº£n dá»‹, má»™c máº¡c", "Äá»‹a Vá»‹"},
                new String[]{"ãŠã—ã‚ƒã‚Œ", "ãŠã—ã‚ƒã‚Œ", "SÃ nh Ä‘iá»‡u, thá»i trang", ""},
                new String[]{"å¤‰", "ã¸ã‚“", "Láº¡, ká»³ quÃ¡i", "Biáº¿n"},
                new String[]{"ä¸æ€è­°", "ãµã—ãŽ", "Ká»³ láº¡, huyá»n bÃ­", "Báº¥t TÆ° Nghá»‹"},
                new String[]{"ã¾ã—", "ã¾ã—", "Tá»‘t hÆ¡n (má»™t chÃºt)", ""},
                new String[]{"ç„¡é§„", "ã‚€ã ", "LÃ£ng phÃ­, vÃ´ Ã­ch", "VÃ´ ÄÃ "},
                new String[]{"è‡ªç”±", "ã˜ã‚†ã†", "Tá»± do", "Tá»± Do"},
                new String[]{"ä¸è‡ªç”±", "ãµã˜ã‚†ã†", "Báº¥t tiá»‡n, tÃ n táº­t", "Báº¥t Tá»± Do"},
                new String[]{"æš–ã¾ã‚‹", "ã‚ãŸãŸã¾ã‚‹", "Trá»Ÿ nÃªn áº¥m Ã¡p", "NoÃ£n"},
                new String[]{"æš–ã‚ã‚‹", "ã‚ãŸãŸã‚ã‚‹", "LÃ m áº¥m", "NoÃ£n"},
                new String[]{"é«˜ã¾ã‚‹", "ãŸã‹ã¾ã‚‹", "TÄƒng lÃªn, cao lÃªn", "Cao"},
                new String[]{"é«˜ã‚ã‚‹", "ãŸã‹ã‚ã‚‹", "NÃ¢ng cao", "Cao"},
                new String[]{"å¼·ã¾ã‚‹", "ã¤ã‚ˆã¾ã‚‹", "Máº¡nh lÃªn", "CÆ°á»ng"},
                new String[]{"å¼·ã‚ã‚‹", "ã¤ã‚ˆã‚ã‚‹", "LÃ m máº¡nh thÃªm", "CÆ°á»ng"},
                new String[]{"å¼±ã¾ã‚‹", "ã‚ˆã‚ã¾ã‚‹", "Yáº¿u Ä‘i", "NhÆ°á»£c"},
                new String[]{"å¼±ã‚ã‚‹", "ã‚ˆã‚ã‚ã‚‹", "LÃ m yáº¿u Ä‘i", "NhÆ°á»£c"},
                new String[]{"åºƒãŒã‚‹", "ã²ã‚ãŒã‚‹", "Má»Ÿ rá»™ng, lan rá»™ng", "Quáº£ng"},
                new String[]{"åºƒã’ã‚‹", "ã²ã‚ã’ã‚‹", "LÃ m rá»™ng ra", "Quáº£ng"},
                new String[]{"æ·±ã¾ã‚‹", "ãµã‹ã¾ã‚‹", "SÃ¢u sáº¯c thÃªm", "ThÃ¢m"},
                new String[]{"æ·±ã‚ã‚‹", "ãµã‹ã‚ã‚‹", "LÃ m sÃ¢u sáº¯c thÃªm", "ThÃ¢m"},
                new String[]{"ä¸–è©±", "ã›ã‚", "Sá»± chÄƒm sÃ³c", "Tháº¿ Thoáº¡i"},
                new String[]{"å®¶åº­", "ã‹ã¦ã„", "Gia Ä‘Ã¬nh", "Gia ÄÃ¬nh"},
                new String[]{"å”åŠ›", "ãã‚‡ã†ã‚Šã‚‡ã", "Há»£p tÃ¡c", "Hiá»‡p Lá»±c"},
                new String[]{"æ„Ÿè¬", "ã‹ã‚“ã—ã‚ƒ", "Sá»± biáº¿t Æ¡n", "Cáº£m Táº¡"},
                new String[]{"è¿·æƒ‘", "ã‚ã„ã‚ã", "Sá»± phiá»n phá»©c", "MÃª Hoáº·c"},
                new String[]{"æŒ¨æ‹¶", "ã‚ã„ã•ã¤", "ChÃ o há»i", "Nhai Láº¡t"},
                new String[]{"è¬ã‚‹", "ã‚ã‚„ã¾ã‚‹", "Xin lá»—i", "Táº¡"},
                new String[]{"ãŠè¾žå„€", "ãŠã˜ãŽ", "CÃºi chÃ o", "Tá»« Nghi"},
                new String[]{"æ¡æ‰‹", "ã‚ãã—ã‚…", "Báº¯t tay", "Ãc Thá»§"},
                new String[]{"æ„åœ°æ‚ª", "ã„ã˜ã‚ã‚‹", "Xáº¥u tÃ­nh", "Ã Äá»‹a Ãc"},
                new String[]{"ã„ãŸãšã‚‰", "ã„ãŸãšã‚‰", "Nghá»‹ch ngá»£m", ""},
                new String[]{"ç¯€ç´„", "ã›ã¤ã‚„ã", "Tiáº¿t kiá»‡m", "Tiáº¿t Æ¯á»›c"},
                new String[]{"çµŒå–¶", "ã‘ã„ãˆã„", "Kinh doanh", "Kinh Doanh"},
                new String[]{"åçœ", "ã¯ã‚“ã›ã„", "Kiá»ƒm Ä‘iá»ƒm láº¡i mÃ¬nh", "Pháº£n Tá»‰nh"},
                new String[]{"å®Ÿè¡Œ", "ã˜ã£ã“ã†", "Thá»±c hÃ nh, tiáº¿n hÃ nh", "Thá»±c HÃ nh"},
                new String[]{"é€²æ­©", "ã—ã‚“ã½", "Tiáº¿n bá»™", "Tiáº¿n Bá»™"},
                new String[]{"å¤‰åŒ–", "ã¸ã‚“ã‹", "Thay Ä‘á»•i", "Biáº¿n HÃ³a"},
                new String[]{"ç™ºé”", "ã¯ã£ãŸã¤", "PhÃ¡t triá»ƒn", "PhÃ¡t Äáº¡t"},
                new String[]{"ä½“åŠ›", "ãŸã„ã‚Šã‚‡ã", "Thá»ƒ lá»±c", "Thá»ƒ Lá»±c"},
                new String[]{"å‡ºå ´", "ã—ã‚…ã¤ã˜ã‚‡ã†", "Tham dá»±, ra sÃ¢n", "Xuáº¥t TrÆ°á»ng"},
                new String[]{"æ´»èº", "ã‹ã¤ã‚„ã", "Hoáº¡t Ä‘á»™ng sÃ´i ná»•i", "Hoáº¡t DÆ°á»£c"},
                new String[]{"ç«¶äº‰", "ãã‚‡ã†ãã†", "Cáº¡nh tranh", "Cáº¡nh Tranh"},
                new String[]{"å¿œæ´", "ãŠã†ãˆã‚“", "Cá»• vÅ©", "á»¨ng Viá»‡n"},
                new String[]{"æ‹æ‰‹", "ã¯ãã—ã‚…", "Vá»— tay", "PhÃ¡ch Thá»§"},
                new String[]{"äººæ°—", "ã«ã‚“ã", "ÄÆ°á»£c hÃ¢m má»™", "NhÃ¢n KhÃ­"},
                new String[]{"å™‚", "ã†ã‚ã•", "Tin Ä‘á»“n", "Äá»“n"},
                new String[]{"æƒ…å ±", "ã˜ã‚‡ã†ã»ã†", "ThÃ´ng tin", "TÃ¬nh BÃ¡o"},
                new String[]{"äº¤æ›", "ã“ã†ã‹ã‚“", "Trao Ä‘á»•i", "Giao HoÃ¡n"},
                new String[]{"æµè¡Œ", "ã‚Šã‚…ã†ã“ã†", "LÆ°u hÃ nh, thá»‹nh hÃ nh", "LÆ°u HÃ nh"},
                new String[]{"å®£ä¼", "ã›ã‚“ã§ã‚“", "TuyÃªn truyá»n", "TuyÃªn Truyá»n"},
                new String[]{"åºƒå‘Š", "ã“ã†ã“ã", "Quáº£ng cÃ¡o", "Quáº£ng CÃ¡o"},
                new String[]{"æ³¨ç›®", "ã¡ã‚…ã†ã‚‚ã", "ChÃº Ã½", "ChÃº Má»¥c"},
                new String[]{"é€šè¨³", "ã¤ã†ã‚„ã", "PhiÃªn dá»‹ch (nÃ³i)", "ThÃ´ng Dá»‹ch"},
                new String[]{"ç¿»è¨³", "ã»ã‚“ã‚„ã", "BiÃªn dá»‹ch (viáº¿t)", "PhiÃªn Dá»‹ch"},
                new String[]{"ä¼è¨€", "ã§ã‚“ã”ã‚“", "Lá»i nháº¯n", "Truyá»n NgÃ´n"},
                new String[]{"å ±å‘Š", "ã»ã†ã“ã", "BÃ¡o cÃ¡o", "BÃ¡o CÃ¡o"},
                new String[]{"éŒ²ç”»", "ã‚ããŒ", "Ghi hÃ¬nh", "Lá»¥c Há»a"},
                new String[]{"æ··é›‘", "ã“ã‚“ã–ã¤", "Táº¯c ngháº½n, Ä‘Ã´ng Ä‘Ãºc", "Há»—n Táº¡p"},
                new String[]{"æ¸‹æ»ž", "ã˜ã‚…ã†ãŸã„", "Táº¯c ngháº½n giao thÃ´ng", "SÃ¡p Trá»‡"}
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
                    .title("Unit 4: TÃ­nh tá»« & PhÃ³ tá»«")
                    .description("82 tá»« vá»±ng N3 vá» chá»§ Ä‘á» tÃ­nh tá»« vÃ  phÃ³ tá»«.")
                    .orderIndex(4)
                    .imageUrl("/assets/hikari_logo.png").level("N3").build();
            unitRepository.save(unit4);
        } else {
            unit4 = unitRepository.findAll().get(3);
        }

        List<String[]> vocabData4 = Arrays.asList(
                new String[]{"æ¿ƒã„", "ã“ã„", "Äáº­m, Ä‘áº·c", "NÃ¹ng"},
                new String[]{"è–„ã„", "ã†ã™ã„", "Má»ng, nháº¡t", "Báº¡c"},
                new String[]{"é…¸ã£ã±ã„", "ã™ã£ã±ã„", "Chua", "Toan"},
                new String[]{"è‡­ã„", "ãã•ã„", "HÃ´i, thá»‘i", "XÃº"},
                new String[]{"ãŠã‹ã—ã„", "ãŠã‹ã—ã„", "Buá»“n cÆ°á»i, ká»³ láº¡", "Kháº£ Tiáº¿u"},
                new String[]{"ã‹ã£ã“ã„ã„", "ã‹ã£ã“ã„ã„", "Äáº¹p trai, báº£nh bao", ""},
                new String[]{"ã†ã¾ã„", "ã†ã¾ã„", "Ngon, giá»i, suÃ´n sáº»", "Má»¹"},
                new String[]{"è¦ªã—ã„", "ã—ãŸã—ã„", "ThÃ¢n thiáº¿t", "ThÃ¢n"},
                new String[]{"è©³ã—ã„", "ãã‚ã—ã„", "Cá»¥ thá»ƒ, chi tiáº¿t", "TÆ°á»ng"},
                new String[]{"ç´°ã‹ã„", "ã“ã¾ã‹ã„", "Nhá», láº», chi tiáº¿t", "Táº¿"},
                new String[]{"æµ…ã„", "ã‚ã•ã„", "NÃ´ng, cáº¡n", "Thiá»ƒn"},
                new String[]{"å›ºã„ãƒ»ç¡¬ã„", "ã‹ãŸã„", "Cá»©ng", "Cá»‘/Ngáº¡nh"},
                new String[]{"ã¬ã‚‹ã„", "ã¬ã‚‹ã„", "Nguá»™i, áº¥m áº¥m", "Ã”n"},
                new String[]{"ã¾ã¶ã—ã„", "ã¾ã¶ã—ã„", "ChÃ³i máº¯t", "Huyá»…n"},
                new String[]{"è’¸ã—æš‘ã„", "ã‚€ã—ã‚ã¤ã„", "Oi bá»©c", "ChÆ°ng Thá»­"},
                new String[]{"æ¸…æ½”ãª", "ã›ã„ã‘ã¤ãª", "Sáº¡ch sáº½", "Thanh Khiáº¿t"},
                new String[]{"æ–°é®®ãª", "ã—ã‚“ã›ã‚“ãª", "TÆ°Æ¡i má»›i", "TÃ¢n TiÃªn"},
                new String[]{"è±Šã‹ãª", "ã‚†ãŸã‹ãª", "Phong phÃº, giÃ u cÃ³", "Phong"},
                new String[]{"ç«‹æ´¾ãª", "ã‚Šã£ã±ãª", "Tuyá»‡t vá»i, hoÃ nh trÃ¡ng", "Láº­p PhÃ¡i"},
                new String[]{"æ­£ç¢ºãª", "ã›ã„ã‹ããª", "ChÃ­nh xÃ¡c", "ChÃ­nh XÃ¡c"},
                new String[]{"ç¢ºã‹ãª", "ãŸã—ã‹ãª", "Cháº¯c cháº¯n, Ä‘Ã­ch thá»±c", "XÃ¡c"},
                new String[]{"é‡è¦ãª", "ã˜ã‚…ã†ã‚ˆã†ãª", "Quan trá»ng", "Trá»ng Yáº¿u"},
                new String[]{"å¿…è¦ãª", "ã²ã¤ã‚ˆã†ãª", "Cáº§n thiáº¿t", "Táº¥t Yáº¿u"},
                new String[]{"ã‚‚ã£ãŸã„ãªã„", "ã‚‚ã£ãŸã„ãªã„", "LÃ£ng phÃ­", ""},
                new String[]{"ã™ã”ã„", "ã™ã”ã„", "Tuyá»‡t vá»i, kinh khá»§ng", ""},
                new String[]{"ã²ã©ã„", "ã²ã©ã„", "Tá»“i tá»‡, khá»§ng khiáº¿p", ""},
                new String[]{"æ¿€ã—ã„", "ã¯ã’ã—ã„", "MÃ£nh liá»‡t, dá»¯ dá»™i", "KhÃ­ch"},
                new String[]{"ãã£ãã‚Šãª", "ãã£ãã‚Šãª", "Giá»‘ng há»‡t nhau", ""},
                new String[]{"æ€¥ãª", "ãã‚…ã†ãª", "Äá»™t ngá»™t, gáº¥p gÃ¡p", "Cáº¥p"},
                new String[]{"é©å½“ãª", "ã¦ãã¨ã†ãª", "ThÃ­ch há»£p, há»i há»£t", "ThÃ­ch ÄÆ°Æ¡ng"},
                new String[]{"ç‰¹åˆ¥ãª", "ã¨ãã¹ã¤ãª", "Äáº·c biá»‡t", "Äáº·c Biá»‡t"},
                new String[]{"å®Œå…¨ãª", "ã‹ã‚“ãœã‚“ãª", "HoÃ n toÃ n", "HoÃ n ToÃ n"},
                new String[]{"ç››ã‚“ãª", "ã•ã‹ã‚“ãª", "Thá»‹nh vÆ°á»£ng, phá»• biáº¿n", "Thá»‹nh"},
                new String[]{"æ§˜ã€…ãª", "ã•ã¾ã–ã¾ãª", "Äa dáº¡ng, nhiá»u loáº¡i", "Dáº¡ng"},
                new String[]{"å¯èƒ½ãª", "ã‹ã®ã†ãª", "CÃ³ thá»ƒ, kháº£ thi", "Kháº£ NÄƒng"},
                new String[]{"ä¸å¯èƒ½ãª", "ãµã‹ã®ã†ãª", "KhÃ´ng thá»ƒ", "Báº¥t Kháº£ NÄƒng"},
                new String[]{"åŸºæœ¬çš„ãª", "ãã»ã‚“ã¦ããª", "TÃ­nh cÆ¡ báº£n", "CÆ¡ Báº£n ÄÃ­ch"},
                new String[]{"å›½éš›çš„ãª", "ã“ãã•ã„ã¦ããª", "TÃ­nh quá»‘c táº¿", "Quá»‘c Táº¿ ÄÃ­ch"},
                new String[]{"ã°ã‚‰ã°ã‚‰ãª", "ã°ã‚‰ã°ã‚‰ãª", "Lá»™n xá»™n, rá»i ráº¡c", ""},
                new String[]{"ã¼ã‚ã¼ã‚ãª", "ã¼ã‚ã¼ã‚ãª", "RÃ¡ch nÃ¡t, tÆ¡i táº£", ""},
                new String[]{"éžå¸¸ã«", "ã²ã˜ã‚‡ã†ã«", "VÃ´ cÃ¹ng, ráº¥t", "Phi ThÆ°á»ng"},
                new String[]{"å¤§å¤‰ã«", "ãŸã„ã¸ã‚“ã«", "Ráº¥t, cá»±c ká»³", "Äáº¡i Biáº¿n"},
                new String[]{"ã»ã¨ã‚“ã©", "ã»ã¨ã‚“ã©", "Háº§u háº¿t, háº§u nhÆ°", ""},
                new String[]{"å¤§ä½“", "ã ã„ãŸã„", "Äáº¡i khÃ¡i", "Äáº¡i Thá»ƒ"},
                new String[]{"ã‹ãªã‚Š", "ã‹ãªã‚Š", "KhÃ¡, tÆ°Æ¡ng Ä‘á»‘i", ""},
                new String[]{"ãšã„ã¶ã‚“", "ãšã„ã¶ã‚“", "ÄÃ¡ng ká»ƒ, nhiá»u", ""},
                new String[]{"ã‘ã£ã“ã†", "ã‘ã£ã“ã†", "KhÃ¡ lÃ ", ""},
                new String[]{"å¤§åˆ†", "ã ã„ã¶", "Pháº§n lá»›n, Ä‘Ã¡ng ká»ƒ", "Äáº¡i PhÃ¢n"},
                new String[]{"ã‚‚ã£ã¨", "ã‚‚ã£ã¨", "HÆ¡n ná»¯a", ""},
                new String[]{"ã™ã£ã‹ã‚Š", "ã™ã£ã‹ã‚Š", "HoÃ n toÃ n, toÃ n bá»™", ""},
                new String[]{"ä¸€æ¯", "ã„ã£ã±ã„", "Äáº§y", "Nháº¥t BÃ´i"},
                new String[]{"ãŽã‚ŠãŽã‚Š", "ãŽã‚ŠãŽã‚Š", "SÃ¡t nÃºt, vá»«a váº·n", ""},
                new String[]{"ã´ã£ãŸã‚Š", "ã´ã£ãŸã‚Š", "Vá»«a khÃ­t, há»£p", ""},
                new String[]{"ãŸã„ã¦ã„", "ãŸã„ã¦ã„", "ThÃ´ng thÆ°á»ng", ""},
                new String[]{"åŒæ™‚ã«", "ã©ã†ã˜ã«", "CÃ¹ng lÃºc", "Äá»“ng Thá»i"},
                new String[]{"å‰ã‚‚ã£ã¦", "ã¾ãˆã‚‚ã£ã¦", "(Chuáº©n bá»‹) trÆ°á»›c", "Tiá»n"},
                new String[]{"ã™ãã«", "ã™ãã«", "Ngay láº­p tá»©c", ""},
                new String[]{"ã‚‚ã†ã™ã", "ã‚‚ã†ã™ã", "Sáº¯p sá»­a", ""},
                new String[]{"çªç„¶", "ã¨ã¤ãœã‚“", "Äá»™t nhiÃªn", "Äá»™t NhiÃªn"},
                new String[]{"ã‚ã£ã¨ã„ã†é–“ã«", "ã‚ã£ã¨ã„ã†ã¾ã«", "Trong nhÃ¡y máº¯t", "Gian"},
                new String[]{"ã„ã¤ã®é–“ã«ã‹", "ã„ã¤ã®ã¾ã«ã‹", "Cháº³ng máº¥y chá»‘c", "Gian"},
                new String[]{"ã—ã°ã‚‰ã", "ã—ã°ã‚‰ã", "Má»™t chá»‘c, má»™t lÃ¡t", ""},
                new String[]{"ãšã£ã¨", "ãšã£ã¨", "Suá»‘t, hÆ¡n háº³n", ""},
                new String[]{"ç›¸å¤‰ã‚ã‚‰ãš", "ã‚ã„ã‹ã‚ã‚‰ãš", "NhÆ° má»i khi", "TÆ°Æ¡ng Biáº¿n"},
                new String[]{"æ¬¡ã€…ã«", "ã¤ãŽã¤ãŽã«", "Láº§n lÆ°á»£t", "Thá»©"},
                new String[]{"ã©ã‚“ã©ã‚“", "ã©ã‚“ã©ã‚“", "Dáº§n dáº§n, Ä‘á»u Ä‘áº·n", ""},
                new String[]{"ã¾ã™ã¾ã™", "ã¾ã™ã¾ã™", "NgÃ y cÃ ng", ""},
                new String[]{"ã‚„ã£ã¨", "ã‚„ã£ã¨", "Cuá»‘i cÃ¹ng thÃ¬ (káº¿t quáº£ tá»‘t)", ""},
                new String[]{"ã¨ã†ã¨ã†", "ã¨ã†ã¨ã†", "Cuá»‘i cÃ¹ng (káº¿t quáº£ xáº¥u/tá»‘t)", ""},
                new String[]{"ã¤ã„ã«", "ã¤ã„ã«", "Cuá»‘i cÃ¹ng", ""},
                new String[]{"ã‚‚ã¡ã‚ã‚“", "ã‚‚ã¡ã‚ã‚“", "Táº¥t nhiÃªn", ""},
                new String[]{"ã‚„ã¯ã‚Š", "ã‚„ã¯ã‚Š", "Quáº£ nhiÃªn", ""},
                new String[]{"ãã£ã¨", "ãã£ã¨", "Nháº¥t Ä‘á»‹nh", ""},
                new String[]{"ãœã²", "ãœã²", "Báº±ng má»i giÃ¡", ""},
                new String[]{"ãªã‚‹ã¹ã", "ãªã‚‹ã¹ã", "Cá»‘ gáº¯ng háº¿t sá»©c", ""},
                new String[]{"æ¡ˆå¤–", "ã‚ã‚“ãŒã„", "KhÃ´ng ngá» Ä‘áº¿n", "Ãn Ngoáº¡i"},
                new String[]{"ã‚‚ã—ã‹ã™ã‚‹ã¨", "ã‚‚ã—ã‹ã™ã‚‹ã¨", "CÃ³ láº½", ""},
                new String[]{"ã¾ã•ã‹", "ã¾ã•ã‹", "Cháº¯c cháº¯n khÃ´ng", ""},
                new String[]{"ã†ã£ã‹ã‚Š", "ã†ã£ã‹ã‚Š", "LÆ¡ Ä‘á»…nh, chá»ƒnh máº£ng", ""},
                new String[]{"ã¤ã„", "ã¤ã„", "VÃ´ Ã½, lá»¡", ""},
                new String[]{"æ€ã‚ãš", "ãŠã‚‚ã‚ãš", "Báº¥t giÃ¡c", "TÆ°"},
                new String[]{"ã»ã£ã¨", "ã»ã£ã¨", "Thá»Ÿ phÃ o nháº¹ nhÃµm", ""}
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

        // Seed Unit 5 (BÃ€I 26)
        Unit unit5;
        if (unitRepository.count() < 5) {
            unit5 = Unit.builder()
                    .title("Unit 5: N4 - BÃ€I 26")
                    .description("Tá»« vá»±ng N4 - BÃ€I 26")
                    .orderIndex(5)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit5);
        } else {
            unit5 = unitRepository.findAll().get(4);
        }
        
        List<String[]> vocabData5 = Arrays.asList(
                new String[]{"è¦‹ã¾ã™ãƒ»è¨ºã¾ã™", "ã¿ã¾ã™", "Xem, khÃ¡m bá»‡nh", "Kiáº¿n / Cháº©n"},
                new String[]{"æŽ¢ã—ã¾ã™ãƒ»æœã—ã¾ã™", "ã•ãŒã—ã¾ã™", "TÃ¬m, tÃ¬m kiáº¿m", "ThÃ¡m / SÆ°u"},
                new String[]{"é…ã‚Œã¾ã™", "ãŠãã‚Œã¾ã™", "Cháº­m trá»…, muá»™n (giá»)", "TrÃ¬"},
                new String[]{"é–“ã«åˆã„ã¾ã™", "ã¾ã«ã‚ã„ã¾ã™", "Ká»‹p (giá»)", "Gian Há»£p"},
                new String[]{"ã‚„ã‚Šã¾ã™", "ã‚„ã‚Šã¾ã™", "LÃ m", ""},
                new String[]{"å‚åŠ ã—ã¾ã™", "ã•ã‚“ã‹ã—ã¾ã™", "Tham gia", "Tham Gia"},
                new String[]{"ç”³ã—è¾¼ã¿ã¾ã™", "ã‚‚ã†ã—ã“ã¿ã¾ã™", "ÄÄƒng kÃ½", "ThÃ¢n VÃ o"},
                new String[]{"éƒ½åˆãŒã„ã„", "ã¤ã”ã†ãŒã„ã„", "Thuáº­n tiá»‡n (vá» thá»i gian)", "ÄÃ´ Há»£p"},
                new String[]{"éƒ½åˆãŒæ‚ªã„", "ã¤ã”ã†ãŒã‚ã‚‹ã„", "Báº¥t tiá»‡n, báº­n", "ÄÃ´ Há»£p Ãc"},
                new String[]{"æ°—åˆ†ãŒã„ã„", "ãã¶ã‚“ãŒã„ã„", "Cáº£m tháº¥y tá»‘t, khá»e", "KhÃ­ PhÃ¢n"},
                new String[]{"æ°—åˆ†ãŒæ‚ªã„", "ãã¶ã‚“ãŒã‚ã‚‹ã„", "Cáº£m tháº¥y khÃ´ng khá»e, má»‡t", "KhÃ­ PhÃ¢n Ãc"},
                new String[]{"æ–°èžç¤¾", "ã—ã‚“ã¶ã‚“ã—ã‚ƒ", "TÃ²a soáº¡n bÃ¡o", "TÃ¢n VÄƒn XÃ£"},
                new String[]{"æŸ”é“", "ã˜ã‚…ã†ã©ã†", "VÃµ Judo", "Nhu Äáº¡o"},
                new String[]{"é‹å‹•ä¼š", "ã†ã‚“ã©ã†ã‹ã„", "Há»™i thi thá»ƒ thao", "Váº­n Äá»™ng Há»™i"},
                new String[]{"å ´æ‰€", "ã°ã—ã‚‡", "Äá»‹a Ä‘iá»ƒm, nÆ¡i chá»‘n", "TrÃ ng Sá»Ÿ"},
                new String[]{"ãƒœãƒ©ãƒ³ãƒ†ã‚£ã‚¢", "ãƒœãƒ©ãƒ³ãƒ†ã‚£ã‚¢", "TÃ¬nh nguyá»‡n viÃªn", ""},
                new String[]{"ï½žå¼", "ï½žã¹ã‚“", "Tiáº¿ng vÃ¹ng ~, giá»ng ~", "Biá»‡n"},
                new String[]{"ä»Šåº¦", "ã“ã‚“ã©", "Láº§n tá»›i", "Kim Äá»™"},
                new String[]{"ãšã„ã¶ã‚“", "ãšã„ã¶ã‚“", "KhÃ¡, tÆ°Æ¡ng Ä‘á»‘i", ""},
                new String[]{"ç›´æŽ¥", "ã¡ã‚‡ãã›ã¤", "Trá»±c tiáº¿p", "Trá»±c Tiáº¿p"},
                new String[]{"ã„ã¤ã§ã‚‚", "ã„ã¤ã§ã‚‚", "LÃºc nÃ o cÅ©ng", ""},
                new String[]{"ã©ã“ã§ã‚‚", "ã©ã“ã§ã‚‚", "á»ž Ä‘Ã¢u cÅ©ng", ""},
                new String[]{"ã ã‚Œã§ã‚‚", "ã ã‚Œã§ã‚‚", "Ai cÅ©ng", ""},
                new String[]{"ãªã‚“ã§ã‚‚", "ãªã‚“ã§ã‚‚", "CÃ¡i gÃ¬ cÅ©ng", ""},
                new String[]{"ã“ã‚“ãªï½ž", "ã“ã‚“ãªï½ž", "~ nhÆ° tháº¿ nÃ y", ""},
                new String[]{"ãã‚“ãªï½ž", "ãã‚“ãªï½ž", "~ nhÆ° tháº¿ Ä‘Ã³", ""},
                new String[]{"ã‚ã‚“ãªï½ž", "ã‚ã‚“ãªï½ž", "~ nhÆ° tháº¿ kia", ""},
                new String[]{"ç‰‡ä»˜ãã¾ã™", "ã‹ãŸã¥ãã¾ã™", "ÄÆ°á»£c dá»n dáº¹p ngÄƒn náº¯p", "Phiáº¿n PhÃ³"},
                new String[]{"ç‡ƒãˆã¾ã™", "ã‚‚ãˆã¾ã™", "ChÃ¡y Ä‘Æ°á»£c (rÃ¡c)", "NhiÃªn"},
                new String[]{"ç½®ãå ´", "ãŠãã°", "NÆ¡i Ä‘á»ƒ", "TrÃ­ TrÃ ng"},
                new String[]{"æ¨ª", "ã‚ˆã“", "BÃªn cáº¡nh", "HoÃ nh"},
                new String[]{"ç“¶", "ã³ã‚“", "CÃ¡i chai", "BÃ¬nh"},
                new String[]{"ç¼¶", "ã‹ã‚“", "CÃ¡i lon, há»™p kim loáº¡i", "Phá»¯u / Can"},
                new String[]{"å®‡å®™", "ã†ã¡ã‚…ã†", "VÅ© trá»¥", "VÅ© Trá»¥"},
                new String[]{"æ€–ã„", "ã“ã‚ã„", "Sá»£", "Bá»‘"}
        );

        if (vocabularyRepository.findByUnitId(unit5.getId()).isEmpty()) {
            for (String[] d : vocabData5) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit5)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 5 with " + vocabData5.size() + " vocabularies ======");
        }

        // Seed Unit 6 (BÃ€I 27)
        Unit unit6;
        if (unitRepository.count() < 6) {
            unit6 = Unit.builder()
                    .title("Unit 6: N4 - BÃ€I 27")
                    .description("Tá»« vá»±ng N4 - BÃ€I 27")
                    .orderIndex(6)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit6);
        } else {
            unit6 = unitRepository.findAll().get(5);
        }
        
        List<String[]> vocabData6 = Arrays.asList(
                new String[]{"é£¼ã„ã¾ã™", "ã‹ã„ã¾ã™", "NuÃ´i (Ä‘á»™ng váº­t)", "Tá»±"},
                new String[]{"èµ°ã‚Šã¾ã™", "ã¯ã—ã‚Šã¾ã™", "Cháº¡y", "Táº©u"},
                new String[]{"è¦‹ãˆã¾ã™", "ã¿ãˆã¾ã™", "CÃ³ thá»ƒ nhÃ¬n tháº¥y", "Kiáº¿n"},
                new String[]{"èžã“ãˆã¾ã™", "ãã“ãˆã¾ã™", "CÃ³ thá»ƒ nghe tháº¥y", "VÄƒn"},
                new String[]{"ã§ãã¾ã™", "ã§ãã¾ã™", "ÄÆ°á»£c hoÃ n thÃ nh, Ä‘Æ°á»£c xÃ¢y lÃªn", ""},
                new String[]{"é–‹ãã¾ã™", "ã²ã‚‰ãã¾ã™", "Má»Ÿ (lá»›p há»c, lá»‘i Ä‘i)", "Khai"},
                new String[]{"å¿ƒé…ï¼ˆãªï¼‰", "ã—ã‚“ã±ã„ï¼ˆãªï¼‰", "Lo láº¯ng", "TÃ¢m Phá»‘i"},
                new String[]{"ãƒšãƒƒãƒˆ", "ãƒšãƒƒãƒˆ", "ThÃº cÆ°ng", ""},
                new String[]{"é³¥", "ã¨ã‚Š", "Con chim", "Äiá»ƒu"},
                new String[]{"å£°", "ã“ãˆ", "Tiáº¿ng, giá»ng nÃ³i", "Thanh"},
                new String[]{"æ³¢", "ãªã¿", "Con sÃ³ng", "Ba"},
                new String[]{"èŠ±ç«", "ã¯ãªã³", "PhÃ¡o hoa", "Hoa Há»a"},
                new String[]{"æ™¯è‰²", "ã‘ã—ã", "Phong cáº£nh", "Cáº£nh Sáº¯c"},
                new String[]{"æ˜¼é–“", "ã²ã‚‹ã¾", "Ban ngÃ y", "TrÃº Gian"},
                new String[]{"æ˜”", "ã‚€ã‹ã—", "NgÃ y xÆ°a", "TÃ­ch"},
                new String[]{"é“å…·", "ã©ã†ã", "Dá»¥ng cá»¥", "Äáº¡o Cá»¥"},
                new String[]{"è‡ªå‹•è²©å£²æ©Ÿ", "ã˜ã©ã†ã¯ã‚“ã°ã„ã", "MÃ¡y bÃ¡n hÃ ng tá»± Ä‘á»™ng", "Tá»± Äá»™ng PhÃ¡n Máº¡i CÆ¡"},
                new String[]{"é€šä¿¡è²©å£²", "ã¤ã†ã—ã‚“ã¯ã‚“ã°ã„", "BÃ¡n hÃ ng qua máº¡ng", "ThÃ´ng TÃ­n PhÃ¡n Máº¡i"},
                new String[]{"ã‚¯ãƒªãƒ¼ãƒ‹ãƒ³ã‚°", "ã‚¯ãƒªãƒ¼ãƒ‹ãƒ³ã‚°", "Giáº·t á»§i", ""},
                new String[]{"ãƒžãƒ³ã‚·ãƒ§ãƒ³", "ãƒžãƒ³ã‚·ãƒ§ãƒ³", "Chung cÆ°", ""},
                new String[]{"å°æ‰€", "ã ã„ã©ã“ã‚", "NhÃ  báº¿p", "ÄÃ i Sá»Ÿ"},
                new String[]{"ï½žæ•™å®¤", "ï½žãã‚‡ã†ã—ã¤", "Lá»›p há»c ~", "GiÃ¡o Tháº¥t"},
                new String[]{"ãƒ‘ãƒ¼ãƒ†ã‚£ãƒ¼ãƒ«ãƒ¼ãƒ ", "ãƒ‘ãƒ¼ãƒ†ã‚£ãƒ¼ãƒ«ãƒ¼ãƒ ", "PhÃ²ng tiá»‡c", ""},
                new String[]{"ï½žå¾Œ", "ï½žã”", "~ sau (khoáº£ng thá»i gian)", "Háº­u"},
                new String[]{"ï½žã—ã‹", "ï½žã—ã‹", "Chá»‰ ~ (luÃ´n Ä‘i vá»›i Phá»§ Ä‘á»‹nh)", ""},
                new String[]{"ã»ã‹ã®", "ã»ã‹ã®", "KhÃ¡c", ""}
        );

        if (vocabularyRepository.findByUnitId(unit6.getId()).isEmpty()) {
            for (String[] d : vocabData6) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit6)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 6 with " + vocabData6.size() + " vocabularies ======");
        }

        // Seed Unit 7 (BÃ€I 28)
        Unit unit7;
        if (unitRepository.count() < 7) {
            unit7 = Unit.builder()
                    .title("Unit 7: N4 - BÃ€I 28")
                    .description("Tá»« vá»±ng N4 - BÃ€I 28")
                    .orderIndex(7)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit7);
        } else {
            unit7 = unitRepository.findAll().get(6);
        }
        
        List<String[]> vocabData7 = Arrays.asList(
                new String[]{"å£²ã‚Œã¾ã™", "ã†ã‚Œã¾ã™", "BÃ¡n cháº¡y, Ä‘Æ°á»£c bÃ¡n", "Máº¡i"},
                new String[]{"è¸Šã‚Šã¾ã™", "ãŠã©ã‚Šã¾ã™", "Nháº£y mÃºa", "DÅ©ng"},
                new String[]{"ã‹ã¿ã¾ã™", "ã‹ã¿ã¾ã™", "Nhai, cáº¯n", ""},
                new String[]{"é¸ã³ã¾ã™", "ãˆã‚‰ã³ã¾ã™", "Lá»±a chá»n", "Tuyá»ƒn"},
                new String[]{"é€šã„ã¾ã™", "ã‹ã‚ˆã„ã¾ã™", "Äi láº¡i (Ä‘i há»c, Ä‘i lÃ m)", "ThÃ´ng"},
                new String[]{"ãƒ¡ãƒ¢ã—ã¾ã™", "ãƒ¡ãƒ¢ã—ã¾ã™", "Ghi chÃº", ""},
                new String[]{"ã¾ã˜ã‚ï¼ˆãªï¼‰", "ã¾ã˜ã‚ï¼ˆãªï¼‰", "NghiÃªm tÃºc, chÄƒm chá»‰", ""},
                new String[]{"ç†±å¿ƒï¼ˆãªï¼‰", "ã­ã£ã—ã‚“ï¼ˆãªï¼‰", "Nhiá»‡t tÃ¬nh", "Nhiá»‡t TÃ¢m"},
                new String[]{"å‰ã„", "ãˆã‚‰ã„", "VÄ© Ä‘áº¡i, giá»i, Ä‘Ã¡ng kÃ­nh", "VÄ©"},
                new String[]{"ã¡ã‚‡ã†ã©ã„ã„", "ã¡ã‚‡ã†ã©ã„ã„", "Vá»«a váº·n, Ä‘Ãºng lÃºc", ""},
                new String[]{"ç¿’æ…£", "ã—ã‚…ã†ã‹ã‚“", "Táº­p quÃ¡n, thÃ³i quen", "Táº­p QuÃ¡n"},
                new String[]{"çµŒé¨“", "ã‘ã„ã‘ã‚“", "Kinh nghiá»‡m", "Kinh Nghiá»‡m"},
                new String[]{"åŠ›", "ã¡ã‹ã‚‰", "Sá»©c máº¡nh, nÄƒng lá»±c", "Lá»±c"},
                new String[]{"äººæ°—", "ã«ã‚“ã", "Sá»± hÃ¢m má»™, Ä‘Æ°á»£c yÃªu thÃ­ch", "NhÃ¢n KhÃ­"},
                new String[]{"å½¢", "ã‹ãŸã¡", "HÃ¬nh dÃ¡ng", "HÃ¬nh"},
                new String[]{"è‰²", "ã„ã‚", "MÃ u sáº¯c", "Sáº¯c"},
                new String[]{"å‘³", "ã‚ã˜", "Vá»‹, mÃ¹i vá»‹", "Vá»‹"},
                new String[]{"ã‚¬ãƒ ", "ã‚¬ãƒ ", "Káº¹o cao su", ""},
                new String[]{"å“ç‰©", "ã—ãªã‚‚ã®", "HÃ ng hÃ³a", "Pháº©m Váº­t"},
                new String[]{"å€¤æ®µ", "ã­ã ã‚“", "GiÃ¡ cáº£", "Trá»‹ Äoáº¡n"},
                new String[]{"çµ¦æ–™", "ãã‚…ã†ã‚Šã‚‡ã†", "LÆ°Æ¡ng", "Cáº¥p Liá»‡u"},
                new String[]{"ãƒœãƒ¼ãƒŠã‚¹", "ãƒœãƒ¼ãƒŠã‚¹", "Tiá»n thÆ°á»Ÿng", ""},
                new String[]{"ç•ªçµ„", "ã°ã‚“ãã¿", "ChÆ°Æ¡ng trÃ¬nh (TV, Radio)", "PhiÃªn Tá»•"},
                new String[]{"ãƒ‰ãƒ©ãƒž", "ãƒ‰ãƒ©ãƒž", "Phim truyá»n hÃ¬nh", ""},
                new String[]{"æ­Œæ‰‹", "ã‹ã—ã‚…", "Ca sÄ©", "Ca Thá»§"},
                new String[]{"å°èª¬", "ã—ã‚‡ã†ã›ã¤", "Tiá»ƒu thuyáº¿t", "Tiá»ƒu Thuyáº¿t"},
                new String[]{"å°èª¬å®¶", "ã—ã‚‡ã†ã›ã¤ã‹", "Tiá»ƒu thuyáº¿t gia", "Tiá»ƒu Thuyáº¿t Gia"},
                new String[]{"ï½žæ©Ÿ", "ï½žã", "MÃ¡y ~", "CÆ¡"},
                new String[]{"æ¯å­", "ã‚€ã™ã“", "Con trai (cá»§a mÃ¬nh)", "Tá»©c Tá»­"},
                new String[]{"æ¯å­ã•ã‚“", "ã‚€ã™ã“ã•ã‚“", "Con trai (ngÆ°á»i khÃ¡c)", "Tá»©c Tá»­"},
                new String[]{"å¨˜", "ã‚€ã™ã‚", "Con gÃ¡i (cá»§a mÃ¬nh)", "NÆ°Æ¡ng"},
                new String[]{"å¨˜ã•ã‚“", "ã‚€ã™ã‚ã•ã‚“", "Con gÃ¡i (ngÆ°á»i khÃ¡c)", "NÆ°Æ¡ng"},
                new String[]{"è‡ªåˆ†", "ã˜ã¶ã‚“", "Báº£n thÃ¢n", "Tá»± PhÃ¢n"},
                new String[]{"å°†æ¥", "ã—ã‚‡ã†ã‚‰ã„", "TÆ°Æ¡ng lai", "TÆ°Æ¡ng Lai"},
                new String[]{"ã—ã°ã‚‰ã", "ã—ã°ã‚‰ã", "Má»™t chá»‘c, má»™t lÃ¡t", ""},
                new String[]{"ãŸã„ã¦ã„", "ãŸã„ã¦ã„", "ThÆ°á»ng, Ä‘áº¡i Ä‘á»ƒ", ""},
                new String[]{"ãã‚Œã«", "ãã‚Œã«", "HÆ¡n ná»¯a, thÃªm vÃ o Ä‘Ã³", ""},
                new String[]{"ãã‚Œã§", "ãã‚Œã§", "Do Ä‘Ã³, vÃ¬ váº­y", ""}
        );

        if (vocabularyRepository.findByUnitId(unit7.getId()).isEmpty()) {
            for (String[] d : vocabData7) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit7)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 7 with " + vocabData7.size() + " vocabularies ======");
        }



        } // Close if (vocabularyRepository.count() == 0)

        // Seed Unit 8 (BÃ€I 29)
        Unit unit8;
        if (unitRepository.count() < 8) {
            unit8 = Unit.builder()
                    .title("Unit 8: N4 - BÃ€I 29")
                    .description("Tá»« vá»±ng N4 - BÃ€I 29")
                    .orderIndex(8)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit8);
        } else {
            unit8 = unitRepository.findAll().get(7);
        }
        
        List<String[]> vocabData8 = Arrays.asList(
            new String[]{"é–‹ãã¾ã™", "ã‚ãã¾ã™", "Má»Ÿ (cá»­a má»Ÿ)", "Khai"},
            new String[]{"é–‰ã¾ã‚Šã¾ã™", "ã—ã¾ã‚Šã¾ã™", "ÄÃ³ng (cá»­a Ä‘Ã³ng)", "Báº¿"},
            new String[]{"ã¤ãã¾ã™", "ã¤ãã¾ã™", "SÃ¡ng (Ä‘iá»‡n sÃ¡ng)", ""},
            new String[]{"æ¶ˆãˆã¾ã™", "ããˆã¾ã™", "Táº¯t (Ä‘iá»‡n táº¯t)", "TiÃªu"},
            new String[]{"å£Šã‚Œã¾ã™", "ã“ã‚ã‚Œã¾ã™", "Há»ng (gháº¿ há»ng)", "Hoáº¡i"},
            new String[]{"å‰²ã‚Œã¾ã™", "ã‚ã‚Œã¾ã™", "Vá»¡ (cá»‘c vá»¡)", "CÃ¡t"},
            new String[]{"æŠ˜ã‚Œã¾ã™", "ãŠã‚Œã¾ã™", "GÃ£y (cÃ¢y gÃ£y)", "Chiáº¿t"},
            new String[]{"ç ´ã‚Œã¾ã™", "ã‚„ã¶ã‚Œã¾ã™", "RÃ¡ch (giáº¥y rÃ¡ch)", "PhÃ¡"},
            new String[]{"æ±šã‚Œã¾ã™", "ã‚ˆã”ã‚Œã¾ã™", "Báº©n (quáº§n Ã¡o báº©n)", "Ã”"},
            new String[]{"ä»˜ãã¾ã™", "ã¤ãã¾ã™", "DÃ­nh, cÃ³ gáº¯n (tÃºi)", "PhÃ³"},
            new String[]{"å¤–ã‚Œã¾ã™", "ã¯ãšã‚Œã¾ã™", "Tuá»™t, bung (cÃºc Ã¡o)", "Ngoáº¡i"},
            new String[]{"æ­¢ã¾ã‚Šã¾ã™", "ã¨ã¾ã‚Šã¾ã™", "Dá»«ng (thang mÃ¡y dá»«ng)", "Chá»‰"},
            new String[]{"ã¾ã¡ãŒãˆã¾ã™", "ã¾ã¡ãŒãˆã¾ã™", "Nháº§m láº«n, sai", ""},
            new String[]{"è½ã¨ã—ã¾ã™", "ãŠã¨ã—ã¾ã™", "LÃ m rÆ¡i", "Láº¡c"},
            new String[]{"æŽ›ã‹ã‚Šã¾ã™", "ã‹ã‹ã‚Šã¾ã™", "KhÃ³a (á»• khÃ³a bá»‹ khÃ³a)", "Quáº£i"},
            new String[]{"ãµãã¾ã™", "ãµãã¾ã™", "Lau, chÃ¹i", ""},
            new String[]{"å–ã‚Šæ›¿ãˆã¾ã™", "ã¨ã‚Šã‹ãˆã¾ã™", "Thay tháº¿", "Thá»§ Tháº¿"},
            new String[]{"ç‰‡ã¥ã‘ã¾ã™", "ã‹ãŸã¥ã‘ã¾ã™", "Dá»n dáº¹p", "Phiáº¿n PhÃ³"},
            new String[]{"çš¿", "ã•ã‚‰", "CÃ¡i Ä‘Ä©a", "MÃ£nh"},
            new String[]{"ã¡ã‚ƒã‚ã‚“", "ã¡ã‚ƒã‚ã‚“", "CÃ¡i bÃ¡t", ""},
            new String[]{"ã‚³ãƒƒãƒ—", "ã‚³ãƒƒãƒ—", "CÃ¡i cá»‘c", ""},
            new String[]{"ã‚¬ãƒ©ã‚¹", "ã‚¬ãƒ©ã‚¹", "KÃ­nh, thá»§y tinh", ""},
            new String[]{"è¢‹", "ãµãã‚", "CÃ¡i tÃºi", "Äáº¡i"},
            new String[]{"æ›¸é¡ž", "ã—ã‚‡ã‚‹ã„", "Giáº¥y tá», tÃ i liá»‡u", "ThÆ° Loáº¡i"},
            new String[]{"æž", "ãˆã ", "CÃ nh cÃ¢y", "Chi"},
            new String[]{"é§…å“¡", "ãˆãã„ã‚“", "NhÃ¢n viÃªn nhÃ  ga", "Dá»‹ch ViÃªn"},
            new String[]{"äº¤ç•ª", "ã“ã†ã°ã‚“", "Äá»“n cáº£nh sÃ¡t", "Giao PhiÃªn"},
            new String[]{"ã‚¹ãƒ”ãƒ¼ãƒ", "ã‚¹ãƒ”ãƒ¼ãƒ", "BÃ i phÃ¡t biá»ƒu", ""},
            new String[]{"è¿”äº‹", "ã¸ã‚“ã˜", "CÃ¢u tráº£ lá»i, pháº£n há»“i", "Pháº£n Sá»±"},
            new String[]{"ãŠå…ˆã«ã©ã†ãž", "ãŠã•ãã«ã©ã†ãž", "Xin má»i Ä‘i trÆ°á»›c", "TiÃªn"}
        );

        if (vocabularyRepository.findByUnitId(unit8.getId()).isEmpty()) {
            for (String[] d : vocabData8) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit8)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 8 with " + vocabData8.size() + " vocabularies ======");
        }

        // Seed Unit 9 (BÃ€I 30)
        Unit unit9;
        if (unitRepository.count() < 9) {
            unit9 = Unit.builder()
                    .title("Unit 9: N4 - BÃ€I 30")
                    .description("Tá»« vá»±ng N4 - BÃ€I 30")
                    .orderIndex(9)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit9);
        } else {
            unit9 = unitRepository.findAll().get(8);
        }
        
        List<String[]> vocabData9 = Arrays.asList(
            new String[]{"å¼µã‚Šã¾ã™", "ã¯ã‚Šã¾ã™", "DÃ¡n", "TrÆ°Æ¡ng"},
            new String[]{"æŽ›ã‘ã¾ã™", "ã‹ã‘ã¾ã™", "Treo", "Quáº£i"},
            new String[]{"é£¾ã‚Šã¾ã™", "ã‹ã–ã‚Šã¾ã™", "Trang trÃ­", "Sá»©c"},
            new String[]{"ä¸¦ã¹ã¾ã™", "ãªã‚‰ã¹ã¾ã™", "Xáº¿p hÃ ng, bÃ y biá»‡n", "Tá»‹nh"},
            new String[]{"æ¤ãˆã¾ã™", "ã†ãˆã¾ã™", "Trá»“ng (cÃ¢y)", "Thá»±c"},
            new String[]{"æˆ»ã—ã¾ã™", "ã‚‚ã©ã—ã¾ã™", "ÄÆ°a vá» chá»— cÅ©", "Lá»‡"},
            new String[]{"ã¾ã¨ã‚ã¾ã™", "ã¾ã¨ã‚ã¾ã™", "TÃ³m táº¯t, táº­p há»£p láº¡i", ""},
            new String[]{"ã—ã¾ã„ã¾ã™", "ã—ã¾ã„ã¾ã™", "Cáº¥t Ä‘i", ""},
            new String[]{"æ±ºã‚ã¾ã™", "ãã‚ã¾ã™", "Quyáº¿t Ä‘á»‹nh", "Quyáº¿t"},
            new String[]{"çŸ¥ã‚‰ã›ã¾ã™", "ã—ã‚‰ã›ã¾ã™", "ThÃ´ng bÃ¡o", "Tri"},
            new String[]{"ç›¸è«‡ã—ã¾ã™", "ãã†ã ã‚“ã—ã¾ã™", "Tháº£o luáº­n, bÃ n báº¡c", "TÆ°Æ¡ng ÄÃ m"},
            new String[]{"äºˆç¿’ã—ã¾ã™", "ã‚ˆã—ã‚…ã†ã—ã¾ã™", "Chuáº©n bá»‹ bÃ i má»›i", "Dá»± Táº­p"},
            new String[]{"å¾©ç¿’ã—ã¾ã™", "ãµãã—ã‚…ã†ã—ã¾ã™", "Ã”n táº­p bÃ i cÅ©", "Phá»¥c Táº­p"},
            new String[]{"æŽˆæ¥­", "ã˜ã‚…ãŽã‚‡ã†", "Giá» há»c", "Thá»¥ Nghiá»‡p"},
            new String[]{"è¬›ç¾©", "ã“ã†ãŽ", "BÃ i giáº£ng", "Giáº£ng NghÄ©a"},
            new String[]{"ãƒŸãƒ¼ãƒ†ã‚£ãƒ³ã‚°", "ãƒŸãƒ¼ãƒ†ã‚£ãƒ³ã‚°", "Cuá»™c há»p", ""},
            new String[]{"äºˆå®š", "ã‚ˆã¦ã„", "Dá»± Ä‘á»‹nh, káº¿ hoáº¡ch", "Dá»± Äá»‹nh"},
            new String[]{"ãŠçŸ¥ã‚‰ã›", "ãŠã—ã‚‰ã›", "Báº£n thÃ´ng bÃ¡o", "Tri"},
            new String[]{"æ¡ˆå†…æ›¸", "ã‚ã‚“ãªã„ã—ã‚‡", "SÃ¡ch/TÃ i liá»‡u hÆ°á»›ng dáº«n", "Ãn Ná»™i ThÆ°"},
            new String[]{"ã‚«ãƒ¬ãƒ³ãƒ€ãƒ¼", "ã‚«ãƒ¬ãƒ³ãƒ€ãƒ¼", "Tá» lá»‹ch", ""},
            new String[]{"ãƒã‚¹ã‚¿ãƒ¼", "ãƒã‚¹ã‚¿ãƒ¼", "Ãp phÃ­ch", ""},
            new String[]{"ã”ã¿ç®±", "ã”ã¿ã°ã“", "ThÃ¹ng rÃ¡c", "TÆ°Æ¡ng"},
            new String[]{"äººå½¢", "ã«ã‚“ãŽã‚‡ã†", "BÃºp bÃª", "NhÃ¢n HÃ¬nh"},
            new String[]{"èŠ±ç“¶", "ã‹ã³ã‚“", "Lá» hoa", "Hoa BÃ¬nh"},
            new String[]{"é¡", "ã‹ãŒã¿", "CÃ¡i gÆ°Æ¡ng", "KÃ­nh"},
            new String[]{"å¼•ãå‡ºã—", "ã²ãã ã—", "NgÄƒn kÃ©o", "Dáº«n Xuáº¥t"},
            new String[]{"çŽ„é–¢", "ã’ã‚“ã‹ã‚“", "Sáº£nh vÃ o nhÃ ", "Huyá»n Quan"},
            new String[]{"å»Šä¸‹", "ã‚ã†ã‹", "HÃ nh lang", "Lang Háº¡"},
            new String[]{"å£", "ã‹ã¹", "Bá»©c tÆ°á»ng", "BÃ­ch"},
            new String[]{"æ± ", "ã„ã‘", "CÃ¡i ao", "TrÃ¬"},
            new String[]{"å…ƒã®æ‰€", "ã‚‚ã¨ã®ã¨ã“ã‚", "Chá»— cÅ©", "NguyÃªn Sá»Ÿ"},
            new String[]{"å‘¨ã‚Š", "ã¾ã‚ã‚Š", "Xung quanh", "Chu"},
            new String[]{"çœŸã‚“ä¸­", "ã¾ã‚“ãªã‹", "ChÃ­nh giá»¯a", "ChÃ¢n Trung"},
            new String[]{"éš…", "ã™ã¿", "GÃ³c", "Ngung"},
            new String[]{"ã¾ã ", "ã¾ã ", "Váº«n (chÆ°a lÃ m gÃ¬)", ""}
        );

        if (vocabularyRepository.findByUnitId(unit9.getId()).isEmpty()) {
            for (String[] d : vocabData9) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit9)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 9 with " + vocabData9.size() + " vocabularies ======");
        }

        // Seed Unit 10 (BÃ€I 31)
        Unit unit10;
        if (unitRepository.count() < 10) {
            unit10 = Unit.builder()
                    .title("Unit 10: N4 - BÃ€I 31")
                    .description("Tá»« vá»±ng N4 - BÃ€I 31")
                    .orderIndex(10)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit10);
        } else {
            unit10 = unitRepository.findAll().get(9);
        }
        
        List<String[]> vocabData10 = Arrays.asList(
            new String[]{"å§‹ã¾ã‚Šã¾ã™", "ã¯ã˜ã¾ã‚Šã¾ã™", "Báº¯t Ä‘áº§u", "Thá»§y"},
            new String[]{"ç¶šã‘ã¾ã™", "ã¤ã¥ã‘ã¾ã™", "Tiáº¿p tá»¥c", "Tá»¥c"},
            new String[]{"è¦‹ã¤ã‘ã¾ã™", "ã¿ã¤ã‘ã¾ã™", "TÃ¬m tháº¥y", "Kiáº¿n"},
            new String[]{"å—ã‘ã¾ã™", "ã†ã‘ã¾ã™", "Nháº­n, dá»± thi", "Thá»¥"},
            new String[]{"å…¥å­¦ã—ã¾ã™", "ã«ã‚…ã†ãŒãã—ã¾ã™", "Nháº­p há»c", "Nháº­p Há»c"},
            new String[]{"å’æ¥­ã—ã¾ã™", "ãã¤ãŽã‚‡ã†ã—ã¾ã™", "Tá»‘t nghiá»‡p", "Tá»‘t Nghiá»‡p"},
            new String[]{"å‡ºå¸­ã—ã¾ã™", "ã—ã‚…ã£ã›ãã—ã¾ã™", "CÃ³ máº·t, tham dá»±", "Xuáº¥t Tá»‹ch"},
            new String[]{"ä¼‘æ†©ã—ã¾ã™", "ãã‚…ã†ã‘ã„ã—ã¾ã™", "Nghá»‰ giáº£i lao", "HÆ°u Kháº¿"},
            new String[]{"é€£ä¼‘", "ã‚Œã‚“ãã‚…ã†", "Ká»³ nghá»‰ dÃ i", "LiÃªn HÆ°u"},
            new String[]{"ä½œæ–‡", "ã•ãã¶ã‚“", "BÃ i vÄƒn", "TÃ¡c VÄƒn"},
            new String[]{"ç™ºè¡¨", "ã¯ã£ã´ã‚‡ã†", "PhÃ¡t biá»ƒu, cÃ´ng bá»‘", "PhÃ¡t Biá»ƒu"},
            new String[]{"å±•è¦§ä¼š", "ã¦ã‚“ã‚‰ã‚“ã‹ã„", "Buá»•i triá»ƒn lÃ£m", "Triá»ƒn LÃ£m Há»™i"},
            new String[]{"çµå©šå¼", "ã‘ã£ã“ã‚“ã—ã", "Lá»… cÆ°á»›i", "Káº¿t HÃ´n Thá»©c"},
            new String[]{"ãŠè‘¬å¼", "ãŠãã†ã—ã", "Lá»… tang", "TÃ¡ng Thá»©c"},
            new String[]{"å¼", "ã—ã", "Buá»•i lá»…", "Thá»©c"},
            new String[]{"æœ¬ç¤¾", "ã»ã‚“ã—ã‚ƒ", "Trá»¥ sá»Ÿ chÃ­nh", "Báº£n XÃ£"},
            new String[]{"æ”¯åº—", "ã—ã¦ã‚“", "Chi nhÃ¡nh", "Chi Äiáº¿m"},
            new String[]{"æ•™ä¼š", "ãã‚‡ã†ã‹ã„", "NhÃ  thá»", "GiÃ¡o Há»™i"},
            new String[]{"å¤§å­¦é™¢", "ã ã„ãŒãã„ã‚“", "Cao há»c", "Äáº¡i Há»c Viá»‡n"},
            new String[]{"å‹•ç‰©åœ’", "ã©ã†ã¶ã¤ãˆã‚“", "Sá»Ÿ thÃº", "Äá»™ng Váº­t ViÃªn"},
            new String[]{"æ¸©æ³‰", "ãŠã‚“ã›ã‚“", "Suá»‘i nÆ°á»›c nÃ³ng", "Ã”n Tuyá»n"},
            new String[]{"å¸°ã‚Š", "ã‹ãˆã‚Š", "LÃºc vá», Ä‘Æ°á»ng vá»", "Quy"},
            new String[]{"ãŠå­ã•ã‚“", "ãŠã“ã•ã‚“", "Con (cá»§a ngÆ°á»i khÃ¡c)", "Tá»­"},
            new String[]{"ï½žå·", "ï½žã”ã†", "Sá»‘ ~ (chuyáº¿n tÃ u, bÃ£o)", "Hiá»‡u"},
            new String[]{"ãšã£ã¨", "ãšã£ã¨", "Suá»‘t, mÃ£i", ""},
            new String[]{"æ®‹ã‚Šã¾ã™", "ã®ã“ã‚Šã¾ã™", "CÃ²n láº¡i, á»Ÿ láº¡i", "TÃ n"},
            new String[]{"æœˆã«", "ã¤ãã«", "Má»™t thÃ¡ng (máº¥y láº§n)", "Nguyá»‡t"},
            new String[]{"æ™®é€šã®", "ãµã¤ã†ã®", "BÃ¬nh thÆ°á»ng, thÃ´ng thÆ°á»ng", "Phá»• ThÃ´ng"},
            new String[]{"ã‚¤ãƒ³ã‚¿ãƒ¼ãƒãƒƒãƒˆ", "ã‚¤ãƒ³ã‚¿ãƒ¼ãƒãƒƒãƒˆ", "Internet", ""},
            new String[]{"æ‘", "ã‚€ã‚‰", "NgÃ´i lÃ ng", "ThÃ´n"},
            new String[]{"æ˜ ç”»é¤¨", "ãˆã„ãŒã‹ã‚“", "Ráº¡p chiáº¿u phim", "Ãnh Há»a QuÃ¡n"}
        );

        if (vocabularyRepository.findByUnitId(unit10.getId()).isEmpty()) {
            for (String[] d : vocabData10) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit10)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 10 with " + vocabData10.size() + " vocabularies ======");
        }

        // Seed Unit 11 (BÃ€I 32)
        Unit unit11;
        if (unitRepository.count() < 11) {
            unit11 = Unit.builder()
                    .title("Unit 11: N4 - BÃ€I 32")
                    .description("Tá»« vá»±ng N4 - BÃ€I 32")
                    .orderIndex(11)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit11);
        } else {
            unit11 = unitRepository.findAll().get(10);
        }
        
        List<String[]> vocabData11 = Arrays.asList(
                new String[]{"é‹å‹•ã—ã¾ã™", "ã†ã‚“ã©ã†ã—ã¾ã™", "Váº­n Ä‘á»™ng, táº­p thá»ƒ dá»¥c", "Váº­n Äá»™ng"},
                new String[]{"æˆåŠŸã—ã¾ã™", "ã›ã„ã“ã†ã—ã¾ã™", "ThÃ nh cÃ´ng", "ThÃ nh CÃ´ng"},
                new String[]{"å¤±æ•—ã—ã¾ã™", "ã—ã£ã±ã„ã—ã¾ã™", "Tháº¥t báº¡i", "Tháº¥t Báº¡i"},
                new String[]{"åˆæ ¼ã—ã¾ã™", "ã”ã†ã‹ãã—ã¾ã™", "Äá»—, trÃºng tuyá»ƒn (ká»³ thi)", "Há»£p CÃ¡ch"},
                new String[]{"æˆ»ã‚Šã¾ã™", "ã‚‚ã©ã‚Šã¾ã™", "Quay láº¡i, trá»Ÿ láº¡i", "Lá»‡"},
                new String[]{"æ™´ã‚Œã¾ã™", "ã¯ã‚Œã¾ã™", "Náº¯ng, quang Ä‘Ã£ng", "TÃ¬nh"},
                new String[]{"æ›‡ã‚Šã¾ã™", "ãã‚‚ã‚Šã¾ã™", "CÃ³ mÃ¢y", "ÄÃ m"},
                new String[]{"å¹ãã¾ã™", "ãµãã¾ã™", "Thá»•i (giÃ³ thá»•i)", "Xuy"},
                new String[]{"æ²»ã‚Šã¾ã™", "ãªãŠã‚Šã¾ã™", "Khá»i (bá»‡nh)", "Trá»‹"},
                new String[]{"ç›´ã‚Šã¾ã™", "ãªãŠã‚Šã¾ã™", "ÄÆ°á»£c sá»­a xong (Ä‘á»“ váº­t)", "Trá»±c"},
                new String[]{"ç¶šãã¾ã™", "ã¤ã¥ãã¾ã™", "Tiáº¿p tá»¥c (sá»‘t)", "Tá»¥c"},
                new String[]{"ã²ãã¾ã™", "ã²ãã¾ã™", "Bá»‹ (cáº£m)", ""},
                new String[]{"å†·ã‚„ã—ã¾ã™", "ã²ã‚„ã—ã¾ã™", "LÃ m láº¡nh", "LÃ£nh"},
                new String[]{"å¿ƒé…ãª", "ã—ã‚“ã±ã„ãª", "Lo láº¯ng", "TÃ¢m Phá»‘i"},
                new String[]{"ååˆ†ãª", "ã˜ã‚…ã†ã¶ã‚“ãª", "Äá»§, Ä‘áº§y Ä‘á»§", "Tháº­p PhÃ¢n"},
                new String[]{"ãŠã‹ã—ã„", "ãŠã‹ã—ã„", "CÃ³ váº¥n Ä‘á», ká»³ láº¡", ""},
                new String[]{"ã†ã‚‹ã•ã„", "ã†ã‚‹ã•ã„", "á»’n Ã o", ""},
                new String[]{"ã‚„ã‘ã©", "ã‚„ã‘ã©", "Bá»ng", ""},
                new String[]{"ã‘ãŒ", "ã‘ãŒ", "Váº¿t thÆ°Æ¡ng", ""},
                new String[]{"ã›ã", "ã›ã", "Ho", ""},
                new String[]{"ã‚¤ãƒ³ãƒ•ãƒ«ã‚¨ãƒ³ã‚¶", "ã‚¤ãƒ³ãƒ•ãƒ«ã‚¨ãƒ³ã‚¶", "Bá»‡nh cÃºm", ""},
                new String[]{"ç©º", "ãã‚‰", "Báº§u trá»i", "KhÃ´ng"},
                new String[]{"å¤ªé™½", "ãŸã„ã‚ˆã†", "Máº·t trá»i", "ThÃ¡i DÆ°Æ¡ng"},
                new String[]{"æ˜Ÿ", "ã»ã—", "NgÃ´i sao", "Tinh"},
                new String[]{"æœˆ", "ã¤ã", "Máº·t trÄƒng", "Nguyá»‡t"},
                new String[]{"é¢¨", "ã‹ãœ", "GiÃ³", "Phong"},
                new String[]{"åŒ—", "ããŸ", "PhÃ­a Báº¯c", "Báº¯c"},
                new String[]{"å—", "ã¿ãªã¿", "PhÃ­a Nam", "Nam"},
                new String[]{"è¥¿", "ã«ã—", "PhÃ­a TÃ¢y", "TÃ¢y"},
                new String[]{"æ±", "ã²ãŒã—", "PhÃ­a ÄÃ´ng", "ÄÃ´ng"},
                new String[]{"æ°´é“", "ã™ã„ã©ã†", "NÆ°á»›c mÃ¡y", "Thá»§y Äáº¡o"},
                new String[]{"ã‚¨ãƒ³ã‚¸ãƒ³", "ã‚¨ãƒ³ã‚¸ãƒ³", "Äá»™ng cÆ¡", ""},
                new String[]{"ãƒãƒ¼ãƒ ", "ãƒãƒ¼ãƒ ", "Äá»™i, nhÃ³m", ""},
                new String[]{"ä»Šå¤œ", "ã“ã‚“ã‚„", "Tá»‘i nay", "Kim Dáº¡"},
                new String[]{"å¤•æ–¹", "ã‚†ã†ãŒãŸ", "Chiá»u tá»‘i", "Tá»‹ch PhÆ°Æ¡ng"},
                new String[]{"ã¾ãˆ", "ã¾ãˆ", "TrÆ°á»›c", ""},
                new String[]{"é…ã", "ãŠãã", "Muá»™n, khuya", "TrÃ¬"},
                new String[]{"ã“ã‚“ãªã«", "ã“ã‚“ãªã«", "NhÆ° tháº¿ nÃ y", ""},
                new String[]{"ãã‚“ãªã«", "ãã‚“ãªã«", "NhÆ° tháº¿ Ä‘Ã³", ""},
                new String[]{"ã‚ã‚“ãªã«", "ã‚ã‚“ãªã«", "NhÆ° tháº¿ kia", ""}
        );

        if (vocabularyRepository.findByUnitId(unit11.getId()).isEmpty()) {
            for (String[] d : vocabData11) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit11)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 11 with " + vocabData11.size() + " vocabularies ======");
        }

        // Seed Unit 12 (BÃ€I 33)
        Unit unit12;
        if (unitRepository.count() < 12) {
            unit12 = Unit.builder()
                    .title("Unit 12: N4 - BÃ€I 33")
                    .description("Tá»« vá»±ng N4 - BÃ€I 33")
                    .orderIndex(12)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit12);
        } else {
            unit12 = unitRepository.findAll().get(11);
        }
        
        List<String[]> vocabData12 = Arrays.asList(
                new String[]{"é€ƒã’ã¾ã™", "ã«ã’ã¾ã™", "Cháº¡y trá»‘n", "ÄÃ o"},
                new String[]{"é¨’ãŽã¾ã™", "ã•ã‚ãŽã¾ã™", "LÃ m á»“n, lÃ m rÃ¹m beng", "Tao"},
                new String[]{"ã‚ãã‚‰ã‚ã¾ã™", "ã‚ãã‚‰ã‚ã¾ã™", "Tá»« bá», bá» cuá»™c", ""},
                new String[]{"æŠ•ã’ã¾ã™", "ãªã’ã¾ã™", "NÃ©m", "Äáº§u"},
                new String[]{"å®ˆã‚Šã¾ã™", "ã¾ã‚‚ã‚Šã¾ã™", "TuÃ¢n thá»§, báº£o vá»‡", "Thá»§"},
                new String[]{"ä¸Šã’ã¾ã™", "ã‚ã’ã¾ã™", "NÃ¢ng lÃªn, tÄƒng lÃªn", "ThÆ°á»£ng"},
                new String[]{"ä¸‹ã’ã¾ã™", "ã•ã’ã¾ã™", "Háº¡ xuá»‘ng, giáº£m xuá»‘ng", "Háº¡"},
                new String[]{"ä¼ãˆã¾ã™", "ã¤ãŸãˆã¾ã™", "Truyá»n Ä‘áº¡t", "Truyá»n"},
                new String[]{"æ³¨æ„ã—ã¾ã™", "ã¡ã‚…ã†ã„ã—ã¾ã™", "ChÃº Ã½ (Ã´ tÃ´)", "ChÃº Ã"},
                new String[]{"å¤–ã—ã¾ã™", "ã¯ãšã—ã¾ã™", "Rá»i (chá»— ngá»“i)", "Ngoáº¡i"},
                new String[]{"ã ã‚ãª", "ã ã‚ãª", "KhÃ´ng Ä‘Æ°á»£c, há»ng", ""},
                new String[]{"å¸­", "ã›ã", "Chá»— ngá»“i", "Tá»‹ch"},
                new String[]{"ãƒ•ã‚¡ã‚¤ãƒˆ", "ãƒ•ã‚¡ã‚¤ãƒˆ", "Cá»‘ lÃªn (fight)", ""},
                new String[]{"ãƒžãƒ¼ã‚¯", "ãƒžãƒ¼ã‚¯", "KÃ½ hiá»‡u (mark)", ""},
                new String[]{"ãƒœãƒ¼ãƒ«", "ãƒœãƒ¼ãƒ«", "Quáº£ bÃ³ng", ""},
                new String[]{"æ´—æ¿¯æ©Ÿ", "ã›ã‚“ãŸãã", "MÃ¡y giáº·t", "Táº©y Tráº¡c CÆ¡"},
                new String[]{"ï½žæ©Ÿ", "ï½žã", "MÃ¡y ~", "CÆ¡"},
                new String[]{"è¦å‰‡", "ããã", "Quy táº¯c, ká»· luáº­t", "Quy Táº¯c"},
                new String[]{"ä½¿ç”¨ç¦æ­¢", "ã—ã‚ˆã†ãã‚“ã—", "Cáº¥m sá»­ dá»¥ng", "Sá»­ Dá»¥ng Cáº¥m"},
                new String[]{"ç«‹å…¥ç¦æ­¢", "ãŸã¡ã„ã‚Šãã‚“ã—", "Cáº¥m vÃ o", "Láº­p Nháº­p Cáº¥m"},
                new String[]{"å…¥å£", "ã„ã‚Šãã¡", "Cá»­a vÃ o", "Nháº­p Kháº©u"},
                new String[]{"å‡ºå£", "ã§ãã¡", "Cá»­a ra", "Xuáº¥t Kháº©u"},
                new String[]{"éžå¸¸å£", "ã²ã˜ã‚‡ã†ãã¡", "Cá»­a thoÃ¡t hiá»ƒm", "Phi ThÆ°á»ng"},
                new String[]{"ç„¡æ–™", "ã‚€ã‚Šã‚‡ã†", "Miá»…n phÃ­", "VÃ´ Liá»‡u"},
                new String[]{"æœ¬æ—¥ä¼‘æ¥­", "ã»ã‚“ã˜ã¤ãã‚…ã†ãŽã‚‡ã†", "HÃ´m nay Ä‘Ã³ng cá»­a", "Báº£n Nháº­t"},
                new String[]{"å–¶æ¥­ä¸­", "ãˆã„ãŽã‚‡ã†ã¡ã‚…ã†", "Äang má»Ÿ cá»­a", "Doanh Nghiá»‡p"},
                new String[]{"ä½¿ç”¨ä¸­", "ã—ã‚ˆã†ã¡ã‚…ã†", "Äang sá»­ dá»¥ng", "Sá»­ Dá»¥ng Trung"},
                new String[]{"ï½žä¸­", "ï½žã¡ã‚…ã†", "Äang ~", "Trung"},
                new String[]{"ã©ã†ã„ã†ï½ž", "ã©ã†ã„ã†ï½ž", "~ tháº¿ nÃ o, Ã½ nghÄ©a gÃ¬", ""},
                new String[]{"ã‚‚ã†", "ã‚‚ã†", "KhÃ´ng ~ ná»¯a (dÃ¹ng vá»›i phá»§ Ä‘á»‹nh)", ""},
                new String[]{"ã‚ã¨ï½ž", "ã‚ã¨ï½ž", "CÃ²n ~", ""},
                new String[]{"é§è»Šé•å", "ã¡ã‚…ã†ã—ã‚ƒã„ã¯ã‚“", "Äá»— xe trÃ¡i phÃ©p", "TrÃº Xa Vi Pháº£n"},
                new String[]{"ãã‚Šã‚ƒã‚", "ãã‚Šã‚ƒã‚", "Tháº¿ thÃ¬, váº­y thÃ¬", ""},
                new String[]{"ï½žä»¥å†…", "ï½žã„ãªã„", "Trong vÃ²ng ~", "DÄ© Ná»™i"},
                new String[]{"è­¦å¯Ÿ", "ã‘ã„ã•ã¤", "Cáº£nh sÃ¡t", "Cáº£nh SÃ¡t"},
                new String[]{"ç½°é‡‘", "ã°ã£ãã‚“", "Tiá»n pháº¡t", "Pháº¡t Kim"},
                new String[]{"é›»å ±", "ã§ã‚“ã½ã†", "Bá»©c Ä‘iá»‡n tÃ­n", "Äiá»‡n BÃ¡o"},
                new String[]{"æ€¥ç—…", "ãã‚…ã†ã³ã‚‡ã†", "Bá»‡nh cáº¥p tÃ­nh", "Cáº¥p Bá»‡nh"},
                new String[]{"é‡ã„ç—…æ°—", "ãŠã‚‚ã„ã³ã‚‡ã†ã", "Bá»‡nh náº·ng", "Trá»ng Bá»‡nh"}
        );

        if (vocabularyRepository.findByUnitId(unit12.getId()).isEmpty()) {
            for (String[] d : vocabData12) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit12)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 12 with " + vocabData12.size() + " vocabularies ======");
        }

        // Seed Unit 13 (BÃ€I 34)
        Unit unit13;
        if (unitRepository.count() < 13) {
            unit13 = Unit.builder()
                    .title("Unit 13: N4 - BÃ€I 34")
                    .description("Tá»« vá»±ng N4 - BÃ€I 34")
                    .orderIndex(13)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit13);
        } else {
            unit13 = unitRepository.findAll().get(12);
        }
        
        List<String[]> vocabData13 = Arrays.asList(
                new String[]{"ç£¨ãã¾ã™", "ã¿ãŒãã¾ã™", "ÄÃ¡nh (rÄƒng), Ä‘Ã¡nh bÃ³ng", "Ma"},
                new String[]{"çµ„ã¿ç«‹ã¦ã¾ã™", "ãã¿ãŸã¦ã¾ã™", "Láº¯p rÃ¡p", "Tá»• Láº­p"},
                new String[]{"æŠ˜ã‚Šã¾ã™", "ãŠã‚Šã¾ã™", "Gáº­p, báº» gÃ£y", "Chiáº¿t"},
                new String[]{"æ°—ãŒã¤ãã¾ã™", "ããŒã¤ãã¾ã™", "Nháº­n ra, Ä‘á»ƒ Ã½", "KhÃ­"},
                new String[]{"ã¤ã‘ã¾ã™", "ã¤ã‘ã¾ã™", "Cháº¥m (xÃ¬ dáº§u)", ""},
                new String[]{"è¦‹ã¤ã‹ã‚Šã¾ã™", "ã¿ã¤ã‹ã‚Šã¾ã™", "ÄÆ°á»£c tÃ¬m tháº¥y", "Kiáº¿n"},
                new String[]{"è³ªå•ã—ã¾ã™", "ã—ã¤ã‚‚ã‚“ã—ã¾ã™", "Há»i, Ä‘áº·t cÃ¢u há»i", "Cháº¥t Váº¥n"},
                new String[]{"ã•ã—ã¾ã™", "ã•ã—ã¾ã™", "Che (Ã´)", ""},
                new String[]{"ã‚¹ãƒãƒ¼ãƒ„ã‚¯ãƒ©ãƒ–", "ã‚¹ãƒãƒ¼ãƒ„ã‚¯ãƒ©ãƒ–", "CÃ¢u láº¡c bá»™ thá»ƒ thao", ""},
                new String[]{"ãŠåŸŽ", "ãŠã—ã‚", "LÃ¢u Ä‘Ã i, thÃ nh", "ThÃ nh"},
                new String[]{"èª¬æ˜Žæ›¸", "ã›ã¤ã‚ã„ã—ã‚‡", "SÃ¡ch hÆ°á»›ng dáº«n", "Thuyáº¿t Minh"},
                new String[]{"å›³", "ãš", "Biá»ƒu Ä‘á»“, sÆ¡ Ä‘á»“", "Äá»“"},
                new String[]{"ç·š", "ã›ã‚“", "ÄÆ°á»ng káº», Ä‘Æ°á»ng dÃ¢y", "Tuyáº¿n"},
                new String[]{"çŸ¢å°", "ã‚„ã˜ã‚‹ã—", "Dáº¥u mÅ©i tÃªn", "Thá»‰ áº¤n"},
                new String[]{"é»’", "ãã‚", "MÃ u Ä‘en", "Háº¯c"},
                new String[]{"ç™½", "ã—ã‚", "MÃ u tráº¯ng", "Báº¡ch"},
                new String[]{"èµ¤", "ã‚ã‹", "MÃ u Ä‘á»", "XÃ­ch"},
                new String[]{"é’", "ã‚ãŠ", "MÃ u xanh da trá»i", "Thanh"},
                new String[]{"ç´º", "ã“ã‚“", "MÃ u xanh tháº«m", "CÃ¡m"},
                new String[]{"é»„è‰²", "ãã„ã‚", "MÃ u vÃ ng", "HoÃ ng Sáº¯c"},
                new String[]{"èŒ¶è‰²", "ã¡ã‚ƒã„ã‚", "MÃ u nÃ¢u", "TrÃ  Sáº¯c"},
                new String[]{"ã—ã‚‡ã†ã‚†", "ã—ã‚‡ã†ã‚†", "XÃ¬ dáº§u", ""},
                new String[]{"ã‚½ãƒ¼ã‚¹", "ã‚½ãƒ¼ã‚¹", "NÆ°á»›c sá»‘t", ""},
                new String[]{"ãŠå®¢ã•ã‚“", "ãŠãã‚ƒãã•ã‚“", "KhÃ¡ch hÃ ng", "KhÃ¡ch"},
                new String[]{"ï½žã‹ï½ž", "ï½žã‹ï½ž", "~ hay lÃ  ~", ""},
                new String[]{"ã‚†ã†ã¹", "ã‚†ã†ã¹", "Tá»‘i hÃ´m qua", ""},
                new String[]{"ã•ã£ã", "ã•ã£ã", "Vá»«a rá»“i, lÃºc nÃ£y", ""},
                new String[]{"èŒ¶é“", "ã•ã©ã†", "TrÃ  Ä‘áº¡o", "TrÃ  Äáº¡o"},
                new String[]{"ãŠèŒ¶ã‚’ãŸã¦ã¾ã™", "ãŠã¡ã‚ƒã‚’ãŸã¦ã¾ã™", "Pha trÃ ", "TrÃ "},
                new String[]{"å…ˆã«", "ã•ãã«", "TrÆ°á»›c", "TiÃªn"},
                new String[]{"è¼‰ã›ã¾ã™", "ã®ã›ã¾ã™", "Äáº·t lÃªn", "TÃ¡i"},
                new String[]{"ã“ã‚Œã§ã„ã„ã§ã™ã‹", "ã“ã‚Œã§ã„ã„ã§ã™ã‹", "Tháº¿ nÃ y cÃ³ Ä‘Æ°á»£c khÃ´ng?", ""},
                new String[]{"ã„ã‹ãŒã§ã™ã‹", "ã„ã‹ãŒã§ã™ã‹", "NhÆ° tháº¿ nÃ o áº¡? (Lá»‹ch sá»± cá»§a ã©ã†ã§ã™ã‹)", ""},
                new String[]{"è‹¦ã„", "ã«ãŒã„", "Äáº¯ng", "Khá»•"}
        );

        if (vocabularyRepository.findByUnitId(unit13.getId()).isEmpty()) {
            for (String[] d : vocabData13) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit13)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 13 with " + vocabData13.size() + " vocabularies ======");
        }

        // Seed Unit 14 (BÃ€I 35)
        Unit unit14;
        if (unitRepository.count() < 14) {
            unit14 = Unit.builder()
                    .title("Unit 14: N4 - BÃ€I 35")
                    .description("Tá»« vá»±ng N4 - BÃ€I 35")
                    .orderIndex(14)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit14);
        } else {
            unit14 = unitRepository.findAll().get(13);
        }
        
        List<String[]> vocabData14 = Arrays.asList(
                new String[]{"å’²ãã¾ã™", "ã•ãã¾ã™", "Ná»Ÿ (hoa)", "Tiáº¿u"},
                new String[]{"å¤‰ã‚ã‚Šã¾ã™", "ã‹ã‚ã‚Šã¾ã™", "Thay Ä‘á»•i (mÃ u)", "Biáº¿n"},
                new String[]{"å›°ã‚Šã¾ã™", "ã“ã¾ã‚Šã¾ã™", "Ráº¯c rá»‘i, khÃ³ khÄƒn", "Khá»‘n"},
                new String[]{"ä»˜ã‘ã¾ã™", "ã¤ã‘ã¾ã™", "ÄÃ¡nh dáº¥u (trÃ²n)", "PhÃ³"},
                new String[]{"æ‹¾ã„ã¾ã™", "ã²ã‚ã„ã¾ã™", "Nháº·t, lÆ°á»£m", "Tháº­p"},
                new String[]{"ã‹ã‹ã‚Šã¾ã™", "ã‹ã‹ã‚Šã¾ã™", "CÃ³ Ä‘iá»‡n thoáº¡i", ""},
                new String[]{"æ¥½ãª", "ã‚‰ããª", "NhÃ n rá»—i, thoáº£i mÃ¡i", "Láº¡c"},
                new String[]{"æ­£ã—ã„", "ãŸã ã—ã„", "ÄÃºng, chÃ­nh xÃ¡c", "ChÃ­nh"},
                new String[]{"çã—ã„", "ã‚ãšã‚‰ã—ã„", "Hiáº¿m cÃ³", "TrÃ¢n"},
                new String[]{"æ–¹", "ã‹ãŸ", "Vá»‹, ngÆ°á»i (lá»‹ch sá»±)", "PhÆ°Æ¡ng"},
                new String[]{"å‘ã“ã†", "ã‚€ã“ã†", "PhÃ­a bÃªn kia", "HÆ°á»›ng"},
                new String[]{"å³¶", "ã—ã¾", "HÃ²n Ä‘áº£o", "Äáº£o"},
                new String[]{"æ‘", "ã‚€ã‚‰", "NgÃ´i lÃ ng", "ThÃ´n"},
                new String[]{"æ¸¯", "ã¿ãªã¨", "Báº¿n cáº£ng", "Cáº£ng"},
                new String[]{"è¿‘æ‰€", "ãã‚“ã˜ã‚‡", "HÃ ng xÃ³m, lÃ¢n cáº­n", "Cáº­n Sá»Ÿ"},
                new String[]{"å±‹ä¸Š", "ãŠãã˜ã‚‡ã†", "SÃ¢n thÆ°á»£ng", "á»c ThÆ°á»£ng"},
                new String[]{"æµ·å¤–", "ã‹ã„ãŒã„", "NÆ°á»›c ngoÃ i", "Háº£i Ngoáº¡i"},
                new String[]{"å±±ç™»ã‚Š", "ã‚„ã¾ã®ã¼ã‚Š", "Leo nÃºi", "SÆ¡n ÄÄƒng"},
                new String[]{"ãƒã‚¤ã‚­ãƒ³ã‚°", "ãƒã‚¤ã‚­ãƒ³ã‚°", "Äi bá»™ leo nÃºi, dÃ£ ngoáº¡i", ""},
                new String[]{"æ©Ÿä¼š", "ãã‹ã„", "CÆ¡ há»™i", "CÆ¡ Há»™i"},
                new String[]{"è¨±å¯", "ãã‚‡ã‹", "Sá»± cho phÃ©p", "Há»©a Kháº£"},
                new String[]{"ä¸¸", "ã¾ã‚‹", "HÃ¬nh trÃ²n", "HoÃ n"},
                new String[]{"æ“ä½œ", "ãã†ã•", "Thao tÃ¡c", "Thao TÃ¡c"},
                new String[]{"æ–¹æ³•", "ã»ã†ã»ã†", "PhÆ°Æ¡ng phÃ¡p", "PhÆ°Æ¡ng PhÃ¡p"},
                new String[]{"è¨­å‚™", "ã›ã¤ã³", "Thiáº¿t bá»‹", "Thiáº¿t Bá»‹"},
                new String[]{"ã‚«ãƒ¼ãƒ†ãƒ³", "ã‚«ãƒ¼ãƒ†ãƒ³", "CÃ¡i rÃ¨m", ""},
                new String[]{"ã²ã‚‚", "ã²ã‚‚", "Sá»£i dÃ¢y", ""},
                new String[]{"ãµãŸ", "ãµãŸ", "CÃ¡i náº¯p", ""},
                new String[]{"è‘‰", "ã¯", "CÃ¡i lÃ¡", "Diá»‡p"},
                new String[]{"æ›²", "ãã‚‡ã", "Báº£n nháº¡c, giai Ä‘iá»‡u", "KhÃºc"},
                new String[]{"æ¥½ã—ã¿", "ãŸã®ã—ã¿", "Sá»± mong Ä‘á»£i, niá»m vui", "Láº¡c"},
                new String[]{"ã‚‚ã£ã¨", "ã‚‚ã£ã¨", "HÆ¡n ná»¯a", ""},
                new String[]{"åˆã‚ã«", "ã¯ã˜ã‚ã«", "TrÆ°á»›c tiÃªn, Ä‘áº§u tiÃªn", "SÆ¡"},
                new String[]{"ã“ã‚Œã§ãŠã‚ã‚Šã¾ã™", "ã“ã‚Œã§ãŠã‚ã‚Šã¾ã™", "Äáº¿n Ä‘Ã¢y lÃ  káº¿t thÃºc", ""},
                new String[]{"ç®±æ ¹", "ã¯ã“ã­", "(Äá»‹a danh á»Ÿ Nháº­t)", "TÆ°Æ¡ng CÄƒn"},
                new String[]{"æ—¥å…‰", "ã«ã£ã“ã†", "(Äá»‹a danh á»Ÿ Nháº­t)", "Nháº­t Quang"},
                new String[]{"ã‚¢ãƒ•ãƒªã‚«", "ã‚¢ãƒ•ãƒªã‚«", "ChÃ¢u Phi", ""},
                new String[]{"å¤œé“", "ã‚ˆã¿ã¡", "ÄÆ°á»ng ban Ä‘Ãªm", "Dáº¡ Äáº¡o"}
        );

        if (vocabularyRepository.findByUnitId(unit14.getId()).isEmpty()) {
            for (String[] d : vocabData14) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit14)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 14 with " + vocabData14.size() + " vocabularies ======");
        }

        // Seed Unit 15 (BÃ€I 36)
        Unit unit15;
        if (unitRepository.count() < 15) {
            unit15 = Unit.builder()
                    .title("Unit 15: N4 - BÃ€I 36")
                    .description("Tá»« vá»±ng N4 - BÃ€I 36")
                    .orderIndex(15)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit15);
        } else {
            unit15 = unitRepository.findAll().get(14);
        }
        
        List<String[]> vocabData15 = Arrays.asList(
                new String[]{"é­ã„ã¾ã™", "ã‚ã„ã¾ã™", "Gáº·p (tai náº¡n)", "Tao"},
                new String[]{"è²¯é‡‘ã—ã¾ã™", "ã¡ã‚‡ãã‚“ã—ã¾ã™", "Tiáº¿t kiá»‡m tiá»n", "Trá»¯ Kim"},
                new String[]{"éŽãŽã¾ã™", "ã™ãŽã¾ã™", "QuÃ¡ (7 giá»)", "QuÃ¡"},
                new String[]{"æ…£ã‚Œã¾ã™", "ãªã‚Œã¾ã™", "LÃ m quen (vá»›i cÃ´ng viá»‡c)", "QuÃ¡n"},
                new String[]{"è…ã‚Šã¾ã™", "ãã•ã‚Šã¾ã™", "Bá»‹ thiu, thá»‘i (thá»©c Äƒn)", "Há»§"},
                new String[]{"å‰£é“", "ã‘ã‚“ã©ã†", "Kiáº¿m Ä‘áº¡o", "Kiáº¿m Äáº¡o"},
                new String[]{"æŸ”é“", "ã˜ã‚…ã†ã©ã†", "Nhu Ä‘áº¡o", "Nhu Äáº¡o"},
                new String[]{"ãƒ©ãƒƒã‚·ãƒ¥", "ãƒ©ãƒƒã‚·ãƒ¥", "Giá» cao Ä‘iá»ƒm", ""},
                new String[]{"å®‡å®™", "ã†ã¡ã‚…ã†", "VÅ© trá»¥", "VÅ© Trá»¥"},
                new String[]{"æ›²", "ãã‚‡ã", "Ca khÃºc, báº£n nháº¡c", "KhÃºc"},
                new String[]{"æ¯Žé€±", "ã¾ã„ã—ã‚…ã†", "Má»—i tuáº§n", "Má»—i Chu"},
                new String[]{"æ¯Žæœˆ", "ã¾ã„ã¤ã", "Má»—i thÃ¡ng", "Má»—i Nguyá»‡t"},
                new String[]{"æ¯Žå¹´", "ã¾ã„ã¨ã— / ã¾ã„ã­ã‚“", "Má»—i nÄƒm", "Má»—i NiÃªn"},
                new String[]{"ã“ã®ã”ã‚", "ã“ã®ã”ã‚", "Dáº¡o nÃ y", ""},
                new String[]{"ã‚„ã£ã¨", "ã‚„ã£ã¨", "Cuá»‘i cÃ¹ng thÃ¬ cÅ©ng", ""},
                new String[]{"ã‹ãªã‚Š", "ã‹ãªã‚Š", "KhÃ¡, tÆ°Æ¡ng Ä‘á»‘i", ""},
                new String[]{"å¿…ãš", "ã‹ãªã‚‰ãš", "Cháº¯c cháº¯n, nháº¥t Ä‘á»‹nh", "Táº¥t"},
                new String[]{"çµ¶å¯¾ã«", "ãœã£ãŸã„ã«", "Tuyá»‡t Ä‘á»‘i (khÃ´ng)", "Tuyá»‡t Äá»‘i"},
                new String[]{"ä¸Šæ‰‹ã«", "ã˜ã‚‡ã†ãšã«", "Giá»i", "ThÆ°á»£ng Thá»§"},
                new String[]{"ã§ãã‚‹ã ã‘", "ã§ãã‚‹ã ã‘", "Cá»‘ gáº¯ng háº¿t sá»©c trong kháº£ nÄƒng", ""},
                new String[]{"ã»ã¨ã‚“ã©", "ã»ã¨ã‚“ã©", "Háº§u háº¿t, pháº§n lá»›n", ""},
                new String[]{"ã‚·ãƒ§ãƒ‘ãƒ³", "ã‚·ãƒ§ãƒ‘ãƒ³", "(Nháº¡c sÄ© Chopin)", ""},
                new String[]{"ãŠå®¢æ§˜", "ãŠãã‚ƒãã•ã¾", "Vá»‹ khÃ¡ch (kÃ­nh ngá»¯)", "KhÃ¡ch Dáº¡ng"},
                new String[]{"ç‰¹åˆ¥", "ã¨ãã¹ã¤", "Äáº·c biá»‡t", "Äáº·c Biá»‡t"},
                new String[]{"ã—ã¦ã„ã‚‰ã£ã—ã‚ƒã„ã¾ã™", "ã—ã¦ã„ã‚‰ã£ã—ã‚ƒã„ã¾ã™", "Äang lÃ m (kÃ­nh ngá»¯)", ""},
                new String[]{"æ°´æ³³", "ã™ã„ãˆã„", "MÃ´n bÆ¡i lá»™i", "Thá»§y Vá»‹nh"},
                new String[]{"é•ã„ã¾ã™", "ã¡ãŒã„ã¾ã™", "KhÃ¡c, sai", "Vi"},
                new String[]{"ä½¿ã£ã¦ã„ã‚‰ã£ã—ã‚ƒã‚‹ã‚“ã§ã™ã­", "ã¤ã‹ã£ã¦ã„ã‚‰ã£ã—ã‚ƒã‚‹ã‚“ã§ã™ã­", "Äang dÃ¹ng pháº£i khÃ´ng áº¡ (kÃ­nh ngá»¯)", "Sá»­"},
                new String[]{"æŒ‘æˆ¦ã—ã¾ã™", "ã¡ã‚‡ã†ã›ã‚“ã—ã¾ã™", "Thá»­ thÃ¡ch, thÃ¡ch thá»©c", "KhiÃªu Chiáº¿n"},
                new String[]{"æ°—æŒã¡", "ãã‚‚ã¡", "Cáº£m giÃ¡c, tÃ¢m tráº¡ng", "KhÃ­ TrÃ¬"},
                new String[]{"ä¹—ã‚Šç‰©", "ã®ã‚Šã‚‚ã®", "PhÆ°Æ¡ng tiá»‡n Ä‘i láº¡i", "Thá»«a Váº­t"},
                new String[]{"ä¸–ç´€", "ã›ã„ã", "Tháº¿ ká»·", "Tháº¿ Ká»·"},
                new String[]{"é ã", "ã¨ãŠã", "Xa", "Viá»…n"}
        );

        if (vocabularyRepository.findByUnitId(unit15.getId()).isEmpty()) {
            for (String[] d : vocabData15) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit15)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 15 with " + vocabData15.size() + " vocabularies ======");
        }

        // Seed Unit 16 (BÃ€I 37)
        Unit unit16;
        if (unitRepository.count() < 16) {
            unit16 = Unit.builder()
                    .title("Unit 16: N4 - BÃ€I 37")
                    .description("Tá»« vá»±ng N4 - BÃ€I 37")
                    .orderIndex(16)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit16);
        } else {
            unit16 = unitRepository.findAll().get(15);
        }
        
        List<String[]> vocabData16 = Arrays.asList(
                new String[]{"è¤’ã‚ã¾ã™", "ã»ã‚ã¾ã™", "Khen ngá»£i", "Bao"},
                new String[]{"ã—ã‹ã‚Šã¾ã™", "ã—ã‹ã‚Šã¾ã™", "Máº¯ng", ""},
                new String[]{"èª˜ã„ã¾ã™", "ã•ãã„ã¾ã™", "Má»i, rá»§ rÃª", "Dá»¥"},
                new String[]{"èµ·ã“ã—ã¾ã™", "ãŠã“ã—ã¾ã™", "ÄÃ¡nh thá»©c", "Khá»Ÿi"},
                new String[]{"æ‹›å¾…ã—ã¾ã™", "ã—ã‚‡ã†ãŸã„ã—ã¾ã™", "Má»i", "ChiÃªu ÄÃ£i"},
                new String[]{"é ¼ã¿ã¾ã™", "ãŸã®ã¿ã¾ã™", "Nhá» váº£", "Láº¡i"},
                new String[]{"æ³¨æ„ã—ã¾ã™", "ã¡ã‚…ã†ã„ã—ã¾ã™", "Nháº¯c nhá»Ÿ, chÃº Ã½", "ChÃº Ã"},
                new String[]{"ã¨ã‚Šã¾ã™", "ã¨ã‚Šã¾ã™", "Láº¥y trá»™m, Äƒn cáº¯p", ""},
                new String[]{"è¸ã¿ã¾ã™", "ãµã¿ã¾ã™", "Dáº«m, Ä‘áº¡p lÃªn", "Äáº¡p"},
                new String[]{"å£Šã—ã¾ã™", "ã“ã‚ã—ã¾ã™", "LÃ m há»ng", "Hoáº¡i"},
                new String[]{"æ±šã—ã¾ã™", "ã‚ˆã”ã—ã¾ã™", "LÃ m báº©n", "Ã”"},
                new String[]{"è¡Œã„ã¾ã™", "ãŠã“ãªã„ã¾ã™", "Tá»• chá»©c, tiáº¿n hÃ nh", "HÃ nh"},
                new String[]{"è¼¸å‡ºã—ã¾ã™", "ã‚†ã—ã‚…ã¤ã—ã¾ã™", "Xuáº¥t kháº©u", "ThÃ¢u Xuáº¥t"},
                new String[]{"è¼¸å…¥ã—ã¾ã™", "ã‚†ã«ã‚…ã†ã—ã¾ã™", "Nháº­p kháº©u", "ThÃ¢u Nháº­p"},
                new String[]{"ç¿»è¨³ã—ã¾ã™", "ã»ã‚“ã‚„ãã—ã¾ã™", "Dá»‹ch thuáº­t (vÄƒn báº£n)", "PhiÃªn Dá»‹ch"},
                new String[]{"ç™ºæ˜Žã—ã¾ã™", "ã¯ã¤ã‚ã„ã—ã¾ã™", "PhÃ¡t minh", "PhÃ¡t Minh"},
                new String[]{"ç™ºè¦‹ã—ã¾ã™", "ã¯ã£ã‘ã‚“ã—ã¾ã™", "PhÃ¡t hiá»‡n", "PhÃ¡t Kiáº¿n"},
                new String[]{"è¨­è¨ˆã—ã¾ã™", "ã›ã£ã‘ã„ã—ã¾ã™", "Thiáº¿t káº¿", "Thiáº¿t Káº¿"},
                new String[]{"ç±³", "ã“ã‚", "Gáº¡o", "Má»…"},
                new String[]{"éº¦", "ã‚€ãŽ", "LÃºa máº¡ch", "Máº¡ch"},
                new String[]{"çŸ³æ²¹", "ã›ãã‚†", "Dáº§u má»", "Tháº¡ch Du"},
                new String[]{"åŽŸæ–™", "ã’ã‚“ã‚Šã‚‡ã†", "NguyÃªn liá»‡u", "NguyÃªn Liá»‡u"},
                new String[]{"ãƒ‡ãƒ¼ãƒˆ", "ãƒ‡ãƒ¼ãƒˆ", "Háº¹n hÃ²", ""},
                new String[]{"æ³¥æ£’", "ã©ã‚ã¼ã†", "Káº» trá»™m", "NÃª Bá»•ng"},
                new String[]{"è­¦å®˜", "ã‘ã„ã‹ã‚“", "Cáº£nh sÃ¡t", "Cáº£nh Quan"},
                new String[]{"å»ºç¯‰å®¶", "ã‘ã‚“ã¡ãã‹", "Kiáº¿n trÃºc sÆ°", "Kiáº¿n TrÃºc Gia"},
                new String[]{"ç§‘å­¦è€…", "ã‹ãŒãã—ã‚ƒ", "NhÃ  khoa há»c", "Khoa Há»c Giáº£"},
                new String[]{"æ¼«ç”»", "ã¾ã‚“ãŒ", "Truyá»‡n tranh", "Máº¡n Há»a"},
                new String[]{"ä¸–ç•Œä¸­", "ã›ã‹ã„ã˜ã‚…ã†", "Kháº¯p tháº¿ giá»›i", "Tháº¿ Giá»›i Trung"},
                new String[]{"ï½žä¸­", "ï½žã˜ã‚…ã†", "Kháº¯p ~", "Trung"},
                new String[]{"ï½žã«ã‚ˆã£ã¦", "ï½žã«ã‚ˆã£ã¦", "Bá»Ÿi ~ (do ai Ä‘Ã³ lÃ m)", ""},
                new String[]{"ã‚ˆã‹ã£ãŸã§ã™ã­", "ã‚ˆã‹ã£ãŸã§ã™ã­", "May quÃ¡ nhá»‰ / Tá»‘t quÃ¡ nhá»‰", ""}
        );

        if (vocabularyRepository.findByUnitId(unit16.getId()).isEmpty()) {
            for (String[] d : vocabData16) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit16)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 16 with " + vocabData16.size() + " vocabularies ======");
        }

        // Seed Unit 17 (BÃ€I 38)
        Unit unit17;
        if (unitRepository.count() < 17) {
            unit17 = Unit.builder()
                    .title("Unit 17: N4 - BÃ€I 38")
                    .description("Tá»« vá»±ng N4 - BÃ€I 38")
                    .orderIndex(17)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit17);
        } else {
            unit17 = unitRepository.findAll().get(16);
        }
        
        List<String[]> vocabData17 = Arrays.asList(
                new String[]{"è‚²ã¦ã¾ã™", "ãã ã¦ã¾ã™", "NuÃ´i dÆ°á»¡ng, trá»“ng", "Dá»¥c"},
                new String[]{"é‹ã³ã¾ã™", "ã¯ã“ã³ã¾ã™", "Váº­n chuyá»ƒn", "Váº­n"},
                new String[]{"äº¡ããªã‚Šã¾ã™", "ãªããªã‚Šã¾ã™", "Máº¥t, qua Ä‘á»i", "Vong"},
                new String[]{"å…¥é™¢ã—ã¾ã™", "ã«ã‚…ã†ã„ã‚“ã—ã¾ã™", "Nháº­p viá»‡n", "Nháº­p Viá»‡n"},
                new String[]{"é€€é™¢ã—ã¾ã™", "ãŸã„ã„ã‚“ã—ã¾ã™", "Xuáº¥t viá»‡n", "ThoÃ¡i Viá»‡n"},
                new String[]{"å…¥ã‚Œã¾ã™", "ã„ã‚Œã¾ã™", "Báº­t (cÃ´ng táº¯c Ä‘iá»‡n)", "Nháº­p"},
                new String[]{"åˆ‡ã‚Šã¾ã™", "ãã‚Šã¾ã™", "Táº¯t (cÃ´ng táº¯c Ä‘iá»‡n)", "Thiáº¿t"},
                new String[]{"æŽ›ã‘ã¾ã™", "ã‹ã‘ã¾ã™", "KhÃ³a (chÃ¬a khÃ³a)", "Quáº£i"},
                new String[]{"æ°—æŒã¡ãŒã„ã„", "ãã‚‚ã¡ãŒã„ã„", "TÃ¢m tráº¡ng tá»‘t, dá»… chá»‹u", "KhÃ­ TrÃ¬"},
                new String[]{"æ°—æŒã¡ãŒæ‚ªã„", "ãã‚‚ã¡ãŒã‚ã‚‹ã„", "TÃ¢m tráº¡ng xáº¥u, buá»“n nÃ´n", "KhÃ­ TrÃ¬ Ãc"},
                new String[]{"å¤§ããªï½ž", "ãŠãŠããªï½ž", "~ to, lá»›n", "Äáº¡i"},
                new String[]{"å°ã•ãªï½ž", "ã¡ã„ã•ãªï½ž", "~ nhá», bÃ©", "Tiá»ƒu"},
                new String[]{"èµ¤ã¡ã‚ƒã‚“", "ã‚ã‹ã¡ã‚ƒã‚“", "Em bÃ©", "XÃ­ch"},
                new String[]{"å°å­¦æ ¡", "ã—ã‚‡ã†ãŒã£ã“ã†", "TrÆ°á»ng tiá»ƒu há»c", "Tiá»ƒu Há»c Hiá»‡u"},
                new String[]{"ä¸­å­¦æ ¡", "ã¡ã‚…ã†ãŒã£ã“ã†", "TrÆ°á»ng trung há»c cÆ¡ sá»Ÿ", "Trung Há»c Hiá»‡u"},
                new String[]{"é§…å‰", "ãˆãã¾ãˆ", "TrÆ°á»›c nhÃ  ga", "Dá»‹ch Tiá»n"},
                new String[]{"æµ·å²¸", "ã‹ã„ãŒã‚“", "Bá» biá»ƒn", "Háº£i Ngáº¡n"},
                new String[]{"ã†ã", "ã†ã", "NÃ³i dá»‘i", ""},
                new String[]{"æ›¸é¡ž", "ã—ã‚‡ã‚‹ã„", "Giáº¥y tá», tÃ i liá»‡u", "ThÆ° Loáº¡i"},
                new String[]{"é›»æº", "ã§ã‚“ã’ã‚“", "Nguá»“n Ä‘iá»‡n", "Äiá»‡n NguyÃªn"},
                new String[]{"è£½", "ã›ã„", "Sáº£n xuáº¥t táº¡i ~", "Cháº¿"},
                new String[]{"ã„ã‘ãªã„", "ã„ã‘ãªã„", "KhÃ´ng Ä‘Æ°á»£c rá»“i! (khi nháº§m láº«n)", ""},
                new String[]{"ãŠå…ˆã«", "ãŠã•ãã«", "(TÃ´i xin phÃ©p vá») trÆ°á»›c", "TiÃªn"},
                new String[]{"åŽŸçˆ†ãƒ‰ãƒ¼ãƒ ", "ã’ã‚“ã°ããƒ‰ãƒ¼ãƒ ", "VÃ²m bom nguyÃªn tá»­ á»Ÿ Hiroshima", "NguyÃªn Báº¡o"},
                new String[]{"å›žè¦§", "ã‹ã„ã‚‰ã‚“", "Báº£n thÃ´ng bÃ¡o tuáº§n hoÃ n", "Há»“i LÃ£m"},
                new String[]{"ç ”ç©¶å®¤", "ã‘ã‚“ãã‚…ã†ã—ã¤", "PhÃ²ng nghiÃªn cá»©u", "NghiÃªn Cá»©u Tháº¥t"},
                new String[]{"ãã¡ã‚“ã¨", "ãã¡ã‚“ã¨", "NgÄƒn náº¯p, cáº©n tháº­n", ""},
                new String[]{"æ•´ç†ã—ã¾ã™", "ã›ã„ã‚Šã—ã¾ã™", "Sáº¯p xáº¿p", "Chá»‰nh LÃ½"},
                new String[]{"æ–¹æ³•", "ã»ã†ã»ã†", "PhÆ°Æ¡ng phÃ¡p", "PhÆ°Æ¡ng PhÃ¡p"},
                new String[]{"ï½žã¨ã„ã†", "ï½žã¨ã„ã†", "Gá»i lÃ  ~", ""},
                new String[]{"å†Š", "ã•ã¤", "Cuá»‘n (Ä‘áº¿m sÃ¡ch vá»Ÿ)", "SÃ¡ch"},
                new String[]{"ã¯ã‚“ã“", "ã¯ã‚“ã“", "Con dáº¥u", ""},
                new String[]{"æŠ¼ã—ã¾ã™", "ãŠã—ã¾ã™", "ÄÃ³ng (dáº¥u)", "Ãp"},
                new String[]{"åŒå­", "ãµãŸã”", "Sinh Ä‘Ã´i", "Song Tá»­"},
                new String[]{"å§‰å¦¹", "ã—ã¾ã„", "Chá»‹ em gÃ¡i", "Tá»· Muá»™i"},
                new String[]{"ä¼¼ã¦ã„ã¾ã™", "ã«ã¦ã„ã¾ã™", "Giá»‘ng nhau", "Tá»±"},
                new String[]{"æ€§æ ¼", "ã›ã„ã‹ã", "TÃ­nh cÃ¡ch", "TÃ­nh CÃ¡ch"},
                new String[]{"ãŠã¨ãªã—ã„", "ãŠã¨ãªã—ã„", "Hiá»n lÃ nh, tráº§m tÃ­nh", ""},
                new String[]{"ä¸–è©±ã‚’ã—ã¾ã™", "ã›ã‚ã‚’ã—ã¾ã™", "ChÄƒm sÃ³c", "Tháº¿ Thoáº¡i"}
        );

        if (vocabularyRepository.findByUnitId(unit17.getId()).isEmpty()) {
            for (String[] d : vocabData17) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit17)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 17 with " + vocabData17.size() + " vocabularies ======");
        }

        // Seed Unit 18 (BÃ€I 39)
        Unit unit18;
        if (unitRepository.count() < 18) {
            unit18 = Unit.builder()
                    .title("Unit 18: N4 - BÃ€I 39")
                    .description("Tá»« vá»±ng N4 - BÃ€I 39")
                    .orderIndex(18)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit18);
        } else {
            unit18 = unitRepository.findAll().get(17);
        }
        
        List<String[]> vocabData18 = Arrays.asList(
                new String[]{"ç­”ãˆã¾ã™", "ã“ãŸãˆã¾ã™", "Tráº£ lá»i", "ÄÃ¡p"},
                new String[]{"å€’ã‚Œã¾ã™", "ãŸãŠã‚Œã¾ã™", "Äá»• (nhÃ  Ä‘á»•)", "Äáº£o"},
                new String[]{"ç„¼ã‘ã¾ã™", "ã‚„ã‘ã¾ã™", "ChÃ¡y (nhÃ  chÃ¡y), nÆ°á»›ng", "ThiÃªu"},
                new String[]{"é€šã‚Šã¾ã™", "ã¨ãŠã‚Šã¾ã™", "Äi qua (Ä‘Æ°á»ng)", "ThÃ´ng"},
                new String[]{"æ­»ã¿ã¾ã™", "ã—ã«ã¾ã™", "Cháº¿t", "Tá»­"},
                new String[]{"ã³ã£ãã‚Šã—ã¾ã™", "ã³ã£ãã‚Šã—ã¾ã™", "Giáº­t mÃ¬nh, ngáº¡c nhiÃªn", ""},
                new String[]{"ãŒã£ã‹ã‚Šã—ã¾ã™", "ãŒã£ã‹ã‚Šã—ã¾ã™", "Tháº¥t vá»ng", ""},
                new String[]{"å®‰å¿ƒã—ã¾ã™", "ã‚ã‚“ã—ã‚“ã—ã¾ã™", "An tÃ¢m", "An TÃ¢m"},
                new String[]{"é…åˆ»ã—ã¾ã™", "ã¡ã“ãã—ã¾ã™", "Äáº¿n muá»™n", "TrÃ¬ Kháº¯c"},
                new String[]{"æ—©é€€ã—ã¾ã™", "ãã†ãŸã„ã—ã¾ã™", "Vá» sá»›m", "Táº£o ThoÃ¡i"},
                new String[]{"ã‘ã‚“ã‹ã—ã¾ã™", "ã‘ã‚“ã‹ã—ã¾ã™", "CÃ£i nhau", ""},
                new String[]{"é›¢å©šã—ã¾ã™", "ã‚Šã“ã‚“ã—ã¾ã™", "Ly hÃ´n", "Ly HÃ´n"},
                new String[]{"è¤‡é›‘ãª", "ãµãã–ã¤ãª", "Phá»©c táº¡p", "Phá»©c Táº¡p"},
                new String[]{"é‚ªé­”ãª", "ã˜ã‚ƒã¾ãª", "Cáº£n trá»Ÿ, vÆ°á»›ng vÃ­u", "TÃ  Ma"},
                new String[]{"æ±šã„", "ããŸãªã„", "Báº©n", "Ã”"},
                new String[]{"ã†ã‚Œã—ã„", "ã†ã‚Œã—ã„", "Vui má»«ng", ""},
                new String[]{"æ‚²ã—ã„", "ã‹ãªã—ã„", "Buá»“n", "Bi"},
                new String[]{"æ¥ãšã‹ã—ã„", "ã¯ãšã‹ã—ã„", "Xáº¥u há»•, e ngáº¡i", "Sá»‰"},
                new String[]{"åœ°éœ‡", "ã˜ã—ã‚“", "Äá»™ng Ä‘áº¥t", "Äá»‹a Cháº¥n"},
                new String[]{"å°é¢¨", "ãŸã„ãµã†", "BÃ£o", "ÄÃ i Phong"},
                new String[]{"ç«äº‹", "ã‹ã˜", "Há»a hoáº¡n", "Há»a Sá»±"},
                new String[]{"äº‹æ•…", "ã˜ã“", "Tai náº¡n", "Sá»± Cá»‘"},
                new String[]{"è¦‹åˆã„", "ã¿ã‚ã„", "Mai má»‘i, xem máº·t", "Kiáº¿n Há»£p"},
                new String[]{"é›»è©±ä»£", "ã§ã‚“ã‚ã ã„", "Tiá»n Ä‘iá»‡n thoáº¡i", "Äiá»‡n Thoáº¡i Äáº¡i"},
                new String[]{"ï½žä»£", "ï½žã ã„", "Tiá»n ~ / PhÃ­ ~", "Äáº¡i"},
                new String[]{"ãƒ•ãƒ­ãƒ³ãƒˆ", "ãƒ•ãƒ­ãƒ³ãƒˆ", "Bá»™ pháº­n tiáº¿p tÃ¢n", ""},
                new String[]{"ï¼å·å®¤", "ï¼ã”ã†ã—ã¤", "PhÃ²ng sá»‘ -", "Hiá»‡u Tháº¥t"},
                new String[]{"æ±—", "ã‚ã›", "Má»“ hÃ´i", "HÃ£n"},
                new String[]{"ã‚¿ã‚ªãƒ«", "ã‚¿ã‚ªãƒ«", "KhÄƒn táº¯m", ""},
                new String[]{"ã›ã£ã‘ã‚“", "ã›ã£ã‘ã‚“", "XÃ  phÃ²ng", ""},
                new String[]{"å¤§å‹¢", "ãŠãŠãœã„", "Nhiá»u ngÆ°á»i", "Äáº¡i Tháº¿"},
                new String[]{"ãŠç–²ã‚Œæ§˜ã§ã—ãŸ", "ãŠã¤ã‹ã‚Œã•ã¾ã§ã—ãŸ", "Cháº¯c anh/chá»‹ Ä‘Ã£ má»‡t rá»“i", "BÃ¬ Dáº¡ng"},
                new String[]{"ä¼ºã„ã¾ã™", "ã†ã‹ãŒã„ã¾ã™", "Äáº¿n thÄƒm (khiÃªm nhÆ°á»ng)", "Tá»©"},
                new String[]{"é€”ä¸­ã§", "ã¨ã¡ã‚…ã†ã§", "Giá»¯a chá»«ng", "Äá»“ Trung"},
                new String[]{"ãƒˆãƒ©ãƒƒã‚¯", "ãƒˆãƒ©ãƒƒã‚¯", "Xe táº£i", ""},
                new String[]{"ã¶ã¤ã‹ã‚Šã¾ã™", "ã¶ã¤ã‹ã‚Šã¾ã™", "Va cháº¡m", ""},
                new String[]{"å¤§äºº", "ãŠã¨ãª", "NgÆ°á»i lá»›n", "Äáº¡i NhÃ¢n"},
                new String[]{"æ´‹æœ", "ã‚ˆã†ãµã", "Ã‚u phá»¥c", "DÆ°Æ¡ng Phá»¥c"},
                new String[]{"è¥¿æ´‹åŒ–ã—ã¾ã™", "ã›ã„ã‚ˆã†ã‹ã—ã¾ã™", "Ã‚u hÃ³a", "TÃ¢y DÆ°Æ¡ng HÃ³a"},
                new String[]{"åˆã„ã¾ã™", "ã‚ã„ã¾ã™", "Vá»«a, há»£p", "Há»£p"},
                new String[]{"ä»Šã§ã¯", "ã„ã¾ã§ã¯", "BÃ¢y giá» thÃ¬", "Kim"},
                new String[]{"æˆäººå¼", "ã›ã„ã˜ã‚“ã—ã", "Lá»… trÆ°á»Ÿng thÃ nh", "ThÃ nh NhÃ¢n Thá»©c"}
        );

        if (vocabularyRepository.findByUnitId(unit18.getId()).isEmpty()) {
            for (String[] d : vocabData18) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit18)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 18 with " + vocabData18.size() + " vocabularies ======");
        }

        // Seed Unit 19 (BÃ€I 40)
        Unit unit19;
        if (unitRepository.count() < 19) {
            unit19 = Unit.builder()
                    .title("Unit 19: N4 - BÃ€I 40")
                    .description("Tá»« vá»±ng N4 - BÃ€I 40")
                    .orderIndex(19)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit19);
        } else {
            unit19 = unitRepository.findAll().get(18);
        }
        
        List<String[]> vocabData19 = Arrays.asList(
                new String[]{"æ•°ãˆã¾ã™", "ã‹ãžãˆã¾ã™", "Äáº¿m", "Sá»‘"},
                new String[]{"æ¸¬ã‚Šã¾ã™", "ã¯ã‹ã‚Šã¾ã™", "Äo (chiá»u dÃ i), cÃ¢n (trá»ng lÆ°á»£ng)", "Tráº¯c"},
                new String[]{"ç¢ºã‹ã‚ã¾ã™", "ãŸã—ã‹ã‚ã¾ã™", "XÃ¡c nháº­n", "XÃ¡c"},
                new String[]{"åˆã„ã¾ã™", "ã‚ã„ã¾ã™", "Vá»«a (kÃ­ch cá»¡)", "Há»£p"},
                new String[]{"å‡ºç™ºã—ã¾ã™", "ã—ã‚…ã£ã±ã¤ã—ã¾ã™", "Xuáº¥t phÃ¡t", "Xuáº¥t PhÃ¡t"},
                new String[]{"åˆ°ç€ã—ã¾ã™", "ã¨ã†ã¡ã‚ƒãã—ã¾ã™", "Äáº¿n nÆ¡i", "ÄÃ¡o TrÆ°á»›c"},
                new String[]{"é…”ã„ã¾ã™", "ã‚ˆã„ã¾ã™", "Say (rÆ°á»£u)", "TÃºy"},
                new String[]{"ã†ã¾ãã„ãã¾ã™", "ã†ã¾ãã„ãã¾ã™", "Thuáº­n lá»£i, trÃ´i cháº£y", ""},
                new String[]{"å‡ºã¾ã™", "ã§ã¾ã™", "CÃ³, xuáº¥t hiá»‡n (váº¥n Ä‘á»)", "Xuáº¥t"},
                new String[]{"ç›¸è«‡ã—ã¾ã™", "ãã†ã ã‚“ã—ã¾ã™", "Tháº£o luáº­n", "TÆ°Æ¡ng ÄÃ m"},
                new String[]{"å¿…è¦ãª", "ã²ã¤ã‚ˆã†ãª", "Cáº§n thiáº¿t", "Táº¥t Yáº¿u"},
                new String[]{"å¤©æ°—äºˆå ±", "ã¦ã‚“ãã‚ˆã»ã†", "Dá»± bÃ¡o thá»i tiáº¿t", "ThiÃªn KhÃ­ Dá»± BÃ¡o"},
                new String[]{"å¿˜å¹´ä¼š", "ã¼ã†ã­ã‚“ã‹ã„", "Tiá»‡c táº¥t niÃªn", "Vong NiÃªn Há»™i"},
                new String[]{"æ–°å¹´ä¼š", "ã—ã‚“ã­ã‚“ã‹ã„", "Tiá»‡c tÃ¢n niÃªn", "TÃ¢n NiÃªn Há»™i"},
                new String[]{"äºŒæ¬¡ä¼š", "ã«ã˜ã‹ã„", "TÄƒng", "Nhá»‹ Thá»© Há»™i"},
                new String[]{"ç™ºè¡¨ä¼š", "ã¯ã£ã´ã‚‡ã†ã‹ã„", "Buá»•i phÃ¡t biá»ƒu", "PhÃ¡t Biá»ƒu Há»™i"},
                new String[]{"å¤§ä¼š", "ãŸã„ã‹ã„", "Äáº¡i há»™i, cuá»™c thi lá»›n", "Äáº¡i Há»™i"},
                new String[]{"ãƒžãƒ©ã‚½ãƒ³", "ãƒžãƒ©ã‚½ãƒ³", "Äiá»n kinh (Marathon)", ""},
                new String[]{"ã‚³ãƒ³ãƒ†ã‚¹ãƒˆ", "ã‚³ãƒ³ãƒ†ã‚¹ãƒˆ", "Cuá»™c thi", ""},
                new String[]{"è¡¨", "ãŠã‚‚ã¦", "Máº·t trÆ°á»›c, bá» máº·t", "Biá»ƒu"},
                new String[]{"è£", "ã†ã‚‰", "Máº·t sau", "LÃ½"},
                new String[]{"ã¾ã¡ãŒã„", "ã¾ã¡ãŒã„", "Lá»—i sai", ""},
                new String[]{"å‚·", "ããš", "Váº¿t thÆ°Æ¡ng, váº¿t xÆ°á»›c", "ThÆ°Æ¡ng"},
                new String[]{"ã‚ºãƒœãƒ³", "ã‚ºãƒœãƒ³", "CÃ¡i quáº§n", ""},
                new String[]{"ãŠå¹´å¯„ã‚Š", "ãŠã¨ã—ã‚ˆã‚Š", "NgÆ°á»i giÃ ", "NiÃªn KÃ½"},
                new String[]{"é•·ã•", "ãªãŒã•", "Chiá»u dÃ i", "TrÆ°á»ng"},
                new String[]{"é‡ã•", "ãŠã‚‚ã•", "Trá»ng lÆ°á»£ng", "Trá»ng"},
                new String[]{"é«˜ã•", "ãŸã‹ã•", "Chiá»u cao", "Cao"},
                new String[]{"å¤§ãã•", "ãŠãŠãã•", "KÃ­ch cá»¡", "Äáº¡i"},
                new String[]{"ï¼ä¾¿", "ï¼ã³ã‚“", "Chuyáº¿n bay sá»‘ -", "Tiá»‡n"},
                new String[]{"ï¼å€‹", "ï¼ã“", "- cÃ¡i, chiáº¿c (Ä‘áº¿m váº­t nhá»)", "CÃ¡"},
                new String[]{"ï¼æœ¬", "ï¼ã»ã‚“", "- cÃ¡i, chiáº¿c (Ä‘áº¿m váº­t dÃ i)", "Báº£n"},
                new String[]{"ï¼æ¯", "ï¼ã¯ã„", "- chÃ©n, cá»‘c", "BÃ´i"},
                new String[]{"ï¼ã‚»ãƒ³ãƒ", "ï¼ã‚»ãƒ³ãƒ", "Centimet", ""},
                new String[]{"ï¼ãƒŸãƒª", "ï¼ãƒŸãƒª", "Milimet", ""},
                new String[]{"ï¼ã‚°ãƒ©ãƒ ", "ï¼ã‚°ãƒ©ãƒ ", "Gram", ""},
                new String[]{"ï½žä»¥ä¸Š", "ï½žã„ã˜ã‚‡ã†", "Trá»Ÿ lÃªn, lá»›n hÆ¡n", "DÄ© ThÆ°á»£ng"},
                new String[]{"ï½žä»¥ä¸‹", "ï½žã„ã‹", "Trá»Ÿ xuá»‘ng, nhá» hÆ¡n", "DÄ© Háº¡"},
                new String[]{"æˆç¸¾", "ã›ã„ã›ã", "ThÃ nh tÃ­ch", "ThÃ nh TÃ­ch"},
                new String[]{"ã¨ã“ã‚ã§", "ã¨ã“ã‚ã§", "", ""}
        );

        if (vocabularyRepository.findByUnitId(unit19.getId()).isEmpty()) {
            for (String[] d : vocabData19) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit19)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 19 with " + vocabData19.size() + " vocabularies ======");
        }

        // Seed Unit 20 (BÃ€I 41)
        Unit unit20;
        if (unitRepository.count() < 20) {
            unit20 = Unit.builder()
                    .title("Unit 20: N4 - BÃ€I 41")
                    .description("Tá»« vá»±ng N4 - BÃ€I 41")
                    .orderIndex(20)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit20);
        } else {
            unit20 = unitRepository.findAll().get(19);
        }
        
        List<String[]> vocabData20 = Arrays.asList(
            new String[]{"ã„ãŸã ãã¾ã™", "ã„ãŸã ãã¾ã™", "Nháº­n (khiÃªm nhÆ°á»ng cá»§a ã‚‚ã‚‰ã„ã¾ã™)", ""},
            new String[]{"ãã ã•ã„ã¾ã™", "ãã ã•ã„ã¾ã™", "Cho, táº·ng (tÃ´n kÃ­nh cá»§a ãã‚Œã¾ã™)", ""},
            new String[]{"ã‚„ã‚Šã¾ã™", "ã‚„ã‚Šã¾ã™", "Cho, táº·ng (dÃ¹ng cho ngÆ°á»i dÆ°á»›i, Ä‘á»™ng váº­t)", ""},
            new String[]{"ä¸Šã’ã¾ã™", "ã‚ã’ã¾ã™", "NÃ¢ng lÃªn, tÄƒng lÃªn", "ThÆ°á»£ng"},
            new String[]{"ä¸‹ã’ã¾ã™", "ã•ã’ã¾ã™", "Háº¡ xuá»‘ng, giáº£m xuá»‘ng", "Háº¡"},
            new String[]{"è¦ªåˆ‡ã«ã—ã¾ã™", "ã—ã‚“ã›ã¤ã«ã—ã¾ã™", "Äá»‘i xá»­ tá»­ táº¿, thÃ¢n thiá»‡n", "ThÃ¢n Thiáº¿t"},
            new String[]{"ã‹ã‚ã„ã„", "ã‹ã‚ã„ã„", "Dá»… thÆ°Æ¡ng", ""},
            new String[]{"çã—ã„", "ã‚ãšã‚‰ã—ã„", "Hiáº¿m, hiáº¿m cÃ³", "TrÃ¢n"},
            new String[]{"ãŠç¥ã„", "ãŠã„ã‚ã„", "QuÃ  chÃºc má»«ng", "ChÃºc"},
            new String[]{"ãŠå¹´çŽ‰", "ãŠã¨ã—ã ã¾", "Tiá»n má»«ng tuá»•i", "NiÃªn Ngá»c"},
            new String[]{"ãŠè¦‹èˆžã„", "ãŠã¿ã¾ã„", "QuÃ  thÄƒm á»‘m", "Kiáº¿n VÅ©"},
            new String[]{"èˆˆå‘³", "ãã‚‡ã†ã¿", "Sá»± há»©ng thÃº, quan tÃ¢m", "HÆ°ng Vá»‹"},
            new String[]{"æƒ…å ±", "ã˜ã‚‡ã†ã»ã†", "ThÃ´ng tin", "TÃ¬nh BÃ¡o"},
            new String[]{"æ–‡æ³•", "ã¶ã‚“ã½ã†", "Ngá»¯ phÃ¡p", "VÄƒn PhÃ¡p"},
            new String[]{"ç™ºéŸ³", "ã¯ã¤ãŠã‚“", "PhÃ¡t Ã¢m", "PhÃ¡t Ã‚m"},
            new String[]{"çŒ¿", "ã•ã‚‹", "Con khá»‰", "ViÃªn"},
            new String[]{"ãˆã•", "ãˆã•", "Thá»©c Äƒn (cho Ä‘á»™ng váº­t)", ""},
            new String[]{"ãŠã‚‚ã¡ã‚ƒ", "ãŠã‚‚ã¡ã‚ƒ", "Äá»“ chÆ¡i", ""},
            new String[]{"çµµæœ¬", "ãˆã»ã‚“", "SÃ¡ch tranh", "Há»™i Báº£n"},
            new String[]{"çµµã¯ãŒã", "ãˆã¯ãŒã", "BÆ°u áº£nh", "Há»™i"},
            new String[]{"ãƒ‰ãƒ©ã‚¤ãƒãƒ¼", "ãƒ‰ãƒ©ã‚¤ãƒãƒ¼", "CÃ¡i tua-vÃ­t", ""},
            new String[]{"ãƒãƒ³ã‚«ãƒ", "ãƒãƒ³ã‚«ãƒ", "KhÄƒn mÃ¹i xoa", ""},
            new String[]{"é´ä¸‹", "ãã¤ã—ãŸ", "BÃ­t táº¥t", "Ngoa Háº¡"},
            new String[]{"æ‰‹è¢‹", "ã¦ã¶ãã‚", "GÄƒng tay", "Thá»§ Äáº¡i"},
            new String[]{"å¹¼ç¨šåœ’", "ã‚ˆã†ã¡ãˆã‚“", "TrÆ°á»ng máº§m non", "áº¤u TrÄ© ViÃªn"},
            new String[]{"æš–æˆ¿", "ã ã‚“ã¼ã†", "MÃ¡y sÆ°á»Ÿi", "NoÃ£n PhÃ²ng"},
            new String[]{"å†·æˆ¿", "ã‚Œã„ã¼ã†", "MÃ¡y láº¡nh", "LÃ£nh PhÃ²ng"},
            new String[]{"æ¸©åº¦", "ãŠã‚“ã©", "Nhiá»‡t Ä‘á»™", "Ã”n Äá»™"},
            new String[]{"ç¥–çˆ¶", "ããµ", "Ã”ng (cá»§a mÃ¬nh)", "Tá»• Phá»¥"},
            new String[]{"ç¥–æ¯", "ãã¼", "BÃ  (cá»§a mÃ¬nh)", "Tá»• Máº«u"},
            new String[]{"å­«", "ã¾ã”", "ChÃ¡u (cá»§a mÃ¬nh)", "TÃ´n"},
            new String[]{"ãŠå­«ã•ã‚“", "ãŠã¾ã”ã•ã‚“", "ChÃ¡u (cá»§a ngÆ°á»i khÃ¡c)", "TÃ´n"},
            new String[]{"ç®¡ç†äºº", "ã‹ã‚“ã‚Šã«ã‚“", "NgÆ°á»i quáº£n lÃ½", "Quáº£n LÃ½ NhÃ¢n"},
            new String[]{"ã“ã®é–“", "ã“ã®ã‚ã„ã ", "Gáº§n Ä‘Ã¢y, hÃ´m ná»", "Gian"},
            new String[]{"ã²ã¨ã“ã¨", "ã²ã¨ã“ã¨", "ÄÃ´i lá»i", ""},
            new String[]{"ï½žãšã¤", "ï½žãšã¤", "Tá»«ng ~, ~ má»™t", ""}
        );

        if (vocabularyRepository.findByUnitId(unit20.getId()).isEmpty()) {
            for (String[] d : vocabData20) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit20)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 20 with " + vocabData20.size() + " vocabularies ======");
        }

        // Seed Unit 21 (BÃ€I 42)
        Unit unit21;
        if (unitRepository.count() < 21) {
            unit21 = Unit.builder()
                    .title("Unit 21: N4 - BÃ€I 42")
                    .description("Tá»« vá»±ng N4 - BÃ€I 42")
                    .orderIndex(21)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit21);
        } else {
            unit21 = unitRepository.findAll().get(20);
        }
        
        List<String[]> vocabData21 = Arrays.asList(
            new String[]{"åŒ…ã¿ã¾ã™", "ã¤ã¤ã¿ã¾ã™", "Bá»c, gÃ³i", "Bao"},
            new String[]{"æ²¸ã‹ã—ã¾ã™", "ã‚ã‹ã—ã¾ã™", "Äun sÃ´i", "PhÃ­"},
            new String[]{"æ··ãœã¾ã™", "ã¾ãœã¾ã™", "Trá»™n, khuáº¥y", "Há»—n"},
            new String[]{"è¨ˆç®—ã—ã¾ã™", "ã‘ã„ã•ã‚“ã—ã¾ã™", "TÃ­nh toÃ¡n", "Káº¿ ToÃ¡n"},
            new String[]{"ä¸¦ã³ã¾ã™", "ãªã‚‰ã³ã¾ã™", "Xáº¿p hÃ ng", "Tá»‹nh"},
            new String[]{"ä¸ˆå¤«ãª", "ã˜ã‚‡ã†ã¶ãª", "Cháº¯c cháº¯n, bá»n", "TrÆ°á»£ng Phu"},
            new String[]{"ã‚¢ãƒ‘ãƒ¼ãƒˆ", "ã‚¢ãƒ‘ãƒ¼ãƒˆ", "CÄƒn há»™", ""},
            new String[]{"å¼è­·å£«", "ã¹ã‚“ã”ã—", "Luáº­t sÆ°", "Biá»‡n Há»™ SÄ©"},
            new String[]{"éŸ³æ¥½å®¶", "ãŠã‚“ãŒãã‹", "Nháº¡c sÄ©", "Ã‚m Nháº¡c Gia"},
            new String[]{"å­ã©ã‚‚ãŸã¡", "ã“ã©ã‚‚ãŸã¡", "Bá»n tráº»", "Tá»­"},
            new String[]{"è‡ªç„¶", "ã—ãœã‚“", "Tá»± nhiÃªn, thiÃªn nhiÃªn", "Tá»± NhiÃªn"},
            new String[]{"æ•™è‚²", "ãã‚‡ã†ã„ã", "GiÃ¡o dá»¥c", "GiÃ¡o Dá»¥c"},
            new String[]{"æ–‡åŒ–", "ã¶ã‚“ã‹", "VÄƒn hÃ³a", "VÄƒn HÃ³a"},
            new String[]{"ç¤¾ä¼š", "ã—ã‚ƒã‹ã„", "XÃ£ há»™i", "XÃ£ Há»™i"},
            new String[]{"æ”¿æ²»", "ã›ã„ã˜", "ChÃ­nh trá»‹", "ChÃ­nh Trá»‹"},
            new String[]{"æ³•å¾‹", "ã»ã†ã‚Šã¤", "PhÃ¡p luáº­t", "PhÃ¡p Luáº­t"},
            new String[]{"æˆ¦äº‰", "ã›ã‚“ãã†", "Chiáº¿n tranh", "Chiáº¿n Tranh"},
            new String[]{"å¹³å’Œ", "ã¸ã„ã‚", "HÃ²a bÃ¬nh", "BÃ¬nh HÃ²a"},
            new String[]{"ç›®çš„", "ã‚‚ãã¦ã", "Má»¥c Ä‘Ã­ch", "Má»¥c ÄÃ­ch"},
            new String[]{"è«–æ–‡", "ã‚ã‚“ã¶ã‚“", "Luáº­n vÄƒn", "Luáº­n VÄƒn"},
            new String[]{"æ¥½ã—ã¿", "ãŸã®ã—ã¿", "Niá»m vui, sá»± mong Ä‘á»£i", "Láº¡c"},
            new String[]{"ãƒŸã‚­ã‚µãƒ¼", "ãƒŸã‚­ã‚µãƒ¼", "MÃ¡y xay sinh tá»‘", ""},
            new String[]{"ã‚„ã‹ã‚“", "ã‚„ã‹ã‚“", "CÃ¡i áº¥m Ä‘un nÆ°á»›c", ""},
            new String[]{"ãµãŸ", "ãµãŸ", "CÃ¡i náº¯p", ""},
            new String[]{"æ “æŠœã", "ã›ã‚“ã¬ã", "CÃ¡i má»Ÿ nÃºt chai", "XuyÃªn Báº¡t"},
            new String[]{"ç¼¶åˆ‡ã‚Š", "ã‹ã‚“ãã‚Š", "CÃ¡i má»Ÿ Ä‘á»“ há»™p", "Phá»¯u Thiáº¿t"},
            new String[]{"ç¼¶è©°", "ã‹ã‚“ã¥ã‚", "Äá»“ há»™p", "Phá»¯u Cáº­t"},
            new String[]{"ã®ã—è¢‹", "ã®ã—ã¶ãã‚", "Phong bÃ¬ (Ä‘á»±ng tiá»n má»«ng)", "Äáº¡i"},
            new String[]{"ãµã‚ã—ã", "ãµã‚ã—ã", "KhÄƒn gÃ³i Ä‘á»“ kiá»ƒu Nháº­t", ""},
            new String[]{"ãã‚ã°ã‚“", "ãã‚ã°ã‚“", "BÃ n tÃ­nh", ""},
            new String[]{"ä½“æ¸©è¨ˆ", "ãŸã„ãŠã‚“ã‘ã„", "Nhiá»‡t káº¿", "Thá»ƒ Ã”n Káº¿"},
            new String[]{"ææ–™", "ã–ã„ã‚Šã‚‡ã†", "NguyÃªn liá»‡u", "TÃ i Liá»‡u"}
        );

        if (vocabularyRepository.findByUnitId(unit21.getId()).isEmpty()) {
            for (String[] d : vocabData21) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit21)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 21 with " + vocabData21.size() + " vocabularies ======");
        }

        // Seed Unit 22 (BÃ€I 43)
        Unit unit22;
        if (unitRepository.count() < 22) {
            unit22 = Unit.builder()
                    .title("Unit 22: N4 - BÃ€I 43")
                    .description("Tá»« vá»±ng N4 - BÃ€I 43")
                    .orderIndex(22)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit22);
        } else {
            unit22 = unitRepository.findAll().get(21);
        }
        
        List<String[]> vocabData22 = Arrays.asList(
            new String[]{"å¢—ãˆã¾ã™", "ãµãˆã¾ã™", "TÄƒng lÃªn", "TÄƒng"},
            new String[]{"æ¸›ã‚Šã¾ã™", "ã¸ã‚Šã¾ã™", "Giáº£m xuá»‘ng", "Giáº£m"},
            new String[]{"ä¸ŠãŒã‚Šã¾ã™", "ã‚ãŒã‚Šã¾ã™", "TÄƒng lÃªn, Ä‘i lÃªn", "ThÆ°á»£ng"},
            new String[]{"ä¸‹ãŒã‚Šã¾ã™", "ã•ãŒã‚Šã¾ã™", "Giáº£m xuá»‘ng, Ä‘i xuá»‘ng", "Háº¡"},
            new String[]{"åˆ‡ã‚Œã¾ã™", "ãã‚Œã¾ã™", "Bá»‹ Ä‘á»©t", "Thiáº¿t"},
            new String[]{"ã¨ã‚Œã¾ã™", "ã¨ã‚Œã¾ã™", "Bá»‹ tuá»™t ra", ""},
            new String[]{"è½ã¡ã¾ã™", "ãŠã¡ã¾ã™", "Bá»‹ rÆ¡i", "Láº¡c"},
            new String[]{"ãªããªã‚Šã¾ã™", "ãªããªã‚Šã¾ã™", "Háº¿t, máº¥t", ""},
            new String[]{"å¤‰ãª", "ã¸ã‚“ãª", "Láº¡, ká»³ quÃ¡i", "Biáº¿n"},
            new String[]{"å¹¸ã›ãª", "ã—ã‚ã‚ã›ãª", "Háº¡nh phÃºc", "Háº¡nh"},
            new String[]{"æ¥½ãª", "ã‚‰ããª", "NhÃ n nhÃ£, thoáº£i mÃ¡i", "Láº¡c"},
            new String[]{"ã†ã¾ã„", "ã†ã¾ã„", "Ngon, giá»i", ""},
            new String[]{"ã¾ãšã„", "ã¾ãšã„", "Dá»Ÿ, khÃ´ng ngon", ""},
            new String[]{"ã¤ã¾ã‚‰ãªã„", "ã¤ã¾ã‚‰ãªã„", "NhÃ m chÃ¡n", ""},
            new String[]{"å„ªã—ã„", "ã‚„ã•ã—ã„", "Hiá»n lÃ nh, dá»‹u dÃ ng", "Æ¯u"},
            new String[]{"ã‚¬ã‚½ãƒªãƒ³", "ã‚¬ã‚½ãƒªãƒ³", "XÄƒng", ""},
            new String[]{"ç«", "ã²", "Lá»­a", "Há»a"},
            new String[]{"ãƒ‘ãƒ³ãƒ•ãƒ¬ãƒƒãƒˆ", "ãƒ‘ãƒ³ãƒ•ãƒ¬ãƒƒãƒˆ", "Tá» rÆ¡i quáº£ng cÃ¡o", ""},
            new String[]{"ä»Šã«ã‚‚", "ã„ã¾ã«ã‚‚", "Báº¥t ká»³ lÃºc nÃ o (sáº¯p)", "Kim"},
            new String[]{"ã‚ã‚", "ã‚ã‚", "á»’, chao Ã´i", ""},
            new String[]{"ã°ã‚‰", "ã°ã‚‰", "Hoa há»“ng", ""},
            new String[]{"ãƒ‰ãƒ©ã‚¤ãƒ–", "ãƒ‰ãƒ©ã‚¤ãƒ–", "LÃ¡i xe Ä‘i chÆ¡i", ""},
            new String[]{"ç†ç”±", "ã‚Šã‚†ã†", "LÃ½ do", "LÃ½ Do"},
            new String[]{"è¬ã‚Šã¾ã™", "ã‚ã‚„ã¾ã‚Šã¾ã™", "Xin lá»—i", "Táº¡"},
            new String[]{"çŸ¥ã‚Šåˆã„ã¾ã™", "ã—ã‚Šã‚ã„ã¾ã™", "Quen biáº¿t", "Tri Há»£p"}
        );

        if (vocabularyRepository.findByUnitId(unit22.getId()).isEmpty()) {
            for (String[] d : vocabData22) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit22)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 22 with " + vocabData22.size() + " vocabularies ======");
        }

        // Seed Unit 23 (BÃ€I 44)
        Unit unit23;
        if (unitRepository.count() < 23) {
            unit23 = Unit.builder()
                    .title("Unit 23: N4 - BÃ€I 44")
                    .description("Tá»« vá»±ng N4 - BÃ€I 44")
                    .orderIndex(23)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit23);
        } else {
            unit23 = unitRepository.findAll().get(22);
        }
        
        List<String[]> vocabData23 = Arrays.asList(
            new String[]{"æ³£ãã¾ã™", "ãªãã¾ã™", "KhÃ³c", "Kháº¥p"},
            new String[]{"ç¬‘ã„ã¾ã™", "ã‚ã‚‰ã„ã¾ã™", "CÆ°á»i", "Tiáº¿u"},
            new String[]{"çœ ã‚Šã¾ã™", "ã­ã‚€ã‚Šã¾ã™", "Ngá»§", "MiÃªn"},
            new String[]{"ä¹¾ãã¾ã™", "ã‹ã‚ãã¾ã™", "KhÃ´", "Can"},
            new String[]{"ã¬ã‚Œã¾ã™", "ã¬ã‚Œã¾ã™", "Æ¯á»›t", ""},
            new String[]{"æ»‘ã‚Šã¾ã™", "ã™ã¹ã‚Šã¾ã™", "TrÆ°á»£t", "Hoáº¡t"},
            new String[]{"èµ·ãã¾ã™", "ãŠãã¾ã™", "Xáº£y ra (tai náº¡n)", "Khá»Ÿi"},
            new String[]{"èª¿ç¯€ã—ã¾ã™", "ã¡ã‚‡ã†ã›ã¤ã—ã¾ã™", "Äiá»u chá»‰nh", "Äiá»u Tiáº¿t"},
            new String[]{"å®‰å…¨ãª", "ã‚ã‚“ãœã‚“ãª", "An toÃ n", "An ToÃ n"},
            new String[]{"ä¸å¯§ãª", "ã¦ã„ã­ã„ãª", "Lá»‹ch sá»±, cáº©n tháº­n", "Äinh Ninh"},
            new String[]{"ç´°ã‹ã„", "ã“ã¾ã‹ã„", "Nhá», láº», chi tiáº¿t", "Táº¿"},
            new String[]{"æ¿ƒã„", "ã“ã„", "Äáº­m, Ä‘áº·c", "NÃ¹ng"},
            new String[]{"è–„ã„", "ã†ã™ã„", "Nháº¡t, má»ng", "Báº¡c"},
            new String[]{"ç©ºæ°—", "ãã†ã", "KhÃ´ng khÃ­", "KhÃ´ng KhÃ­"},
            new String[]{"æ¶™", "ãªã¿ã ", "NÆ°á»›c máº¯t", "Lá»‡"},
            new String[]{"å’Œé£Ÿ", "ã‚ã—ã‚‡ã", "MÃ³n Äƒn Nháº­t", "HÃ²a Thá»±c"},
            new String[]{"æ´‹é£Ÿ", "ã‚ˆã†ã—ã‚‡ã", "MÃ³n Äƒn Ã‚u", "DÆ°Æ¡ng Thá»±c"},
            new String[]{"ãŠã‹ãš", "ãŠã‹ãš", "Thá»©c Äƒn (Äƒn vá»›i cÆ¡m)", ""},
            new String[]{"é‡", "ã‚Šã‚‡ã†", "LÆ°á»£ng, sá»‘ lÆ°á»£ng", "LÆ°á»£ng"},
            new String[]{"ï½žå€", "ï½žã°ã„", "Gáº¥p ~ láº§n", "Bá»™i"},
            new String[]{"åŠåˆ†", "ã¯ã‚“ã¶ã‚“", "Má»™t ná»­a", "BÃ¡n PhÃ¢n"},
            new String[]{"ã‚·ãƒ³ã‚°ãƒ«", "ã‚·ãƒ³ã‚°ãƒ«", "PhÃ²ng Ä‘Æ¡n", ""},
            new String[]{"ãƒ„ã‚¤ãƒ³", "ãƒ„ã‚¤ãƒ³", "PhÃ²ng Ä‘Ã´i", ""},
            new String[]{"æ´—æ¿¯ç‰©", "ã›ã‚“ãŸãã‚‚ã®", "Äá»“ giáº·t", "Táº©y Tráº¡c Váº­t"},
            new String[]{"ç†ç”±", "ã‚Šã‚†ã†", "LÃ½ do", "LÃ½ Do"}
        );

        if (vocabularyRepository.findByUnitId(unit23.getId()).isEmpty()) {
            for (String[] d : vocabData23) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit23)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 23 with " + vocabData23.size() + " vocabularies ======");
        }

        // Seed Unit 24 (BÃ€I 45)
        Unit unit24;
        if (unitRepository.count() < 24) {
            unit24 = Unit.builder()
                    .title("Unit 24: N4 - BÃ€I 45")
                    .description("Tá»« vá»±ng N4 - BÃ€I 45")
                    .orderIndex(24)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit24);
        } else {
            unit24 = unitRepository.findAll().get(23);
        }
        
        List<String[]> vocabData24 = Arrays.asList(
            new String[]{"ä¿¡ã˜ã¾ã™", "ã—ã‚“ã˜ã¾ã™", "Tin tÆ°á»Ÿng", "TÃ­n"},
            new String[]{"ã‚­ãƒ£ãƒ³ã‚»ãƒ«ã—ã¾ã™", "ã‚­ãƒ£ãƒ³ã‚»ãƒ«ã—ã¾ã™", "Há»§y bá»", ""},
            new String[]{"çŸ¥ã‚‰ã›ã¾ã™", "ã—ã‚‰ã›ã¾ã™", "ThÃ´ng bÃ¡o", "Tri"},
            new String[]{"ä¿è¨¼æ›¸", "ã»ã—ã‚‡ã†ã—ã‚‡", "Giáº¥y báº£o hÃ nh", "Báº£o Chá»©ng ThÆ°"},
            new String[]{"é ˜åŽæ›¸", "ã‚Šã‚‡ã†ã—ã‚…ã†ã—ã‚‡", "HÃ³a Ä‘Æ¡n", "LÄ©nh Thu ThÆ°"},
            new String[]{"ã‚­ãƒ£ãƒ³ãƒ—", "ã‚­ãƒ£ãƒ³ãƒ—", "Tráº¡i, cáº¯m tráº¡i", ""},
            new String[]{"ä¸­æ­¢", "ã¡ã‚…ã†ã—", "Dá»«ng, Ä‘Ã¬nh chá»‰", "Trung Chá»‰"},
            new String[]{"ç‚¹", "ã¦ã‚“", "Äiá»ƒm, Ä‘iá»ƒm sá»‘", "Äiá»ƒm"},
            new String[]{"æ¢…é›¨", "ã¤ã‚†", "MÃ¹a mÆ°a", "Mai VÅ©"},
            new String[]{"110ç•ª", "ã²ã‚ƒãã¨ãŠã°ã‚“", "Sá»‘ 110 (Cáº£nh sÃ¡t)", "PhiÃªn"},
            new String[]{"119ç•ª", "ã²ã‚ƒãã˜ã‚…ã†ãã‚…ã†ã°ã‚“", "Sá»‘ 119 (Cá»©u há»a/Cáº¥p cá»©u)", "PhiÃªn"},
            new String[]{"æ€¥ã«", "ãã‚…ã†ã«", "Äá»™t nhiÃªn", "Cáº¥p"},
            new String[]{"ç„¡ç†ã«", "ã‚€ã‚Šã«", "Cá»‘, Ã©p buá»™c", "VÃ´ LÃ½"},
            new String[]{"æ¥½ã—ã¿ã«ã—ã¦ã„ã¾ã™", "ãŸã®ã—ã¿ã«ã—ã¦ã„ã¾ã™", "TrÃ´ng Ä‘á»£i, mong chá»", "Láº¡c"},
            new String[]{"ä»¥ä¸Šã§ã™", "ã„ã˜ã‚‡ã†ã§ã™", "Xin háº¿t", "DÄ© ThÆ°á»£ng"},
            new String[]{"ä¿‚å“¡", "ã‹ã‹ã‚Šã„ã‚“", "NhÃ¢n viÃªn phá»¥ trÃ¡ch", "Há»‡ ViÃªn"},
            new String[]{"ã‚³ãƒ¼ã‚¹", "ã‚³ãƒ¼ã‚¹", "ÄÆ°á»ng cháº¡y, khÃ³a há»c", ""},
            new String[]{"ã‚¹ã‚¿ãƒ¼ãƒˆ", "ã‚¹ã‚¿ãƒ¼ãƒˆ", "Xuáº¥t phÃ¡t", ""},
            new String[]{"ï¼ä½", "ï¼ã„", "Thá»© -, vá»‹ trÃ­ thá»© -", "Vá»‹"},
            new String[]{"å„ªå‹ã—ã¾ã™", "ã‚†ã†ã—ã‚‡ã†ã—ã¾ã™", "VÃ´ Ä‘á»‹ch, chiáº¿n tháº¯ng", "Æ¯u Tháº¯ng"}
        );

        if (vocabularyRepository.findByUnitId(unit24.getId()).isEmpty()) {
            for (String[] d : vocabData24) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit24)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 24 with " + vocabData24.size() + " vocabularies ======");
        }

        // Seed Unit 25 (BÃ€I 46)
        Unit unit25;
        if (unitRepository.count() < 25) {
            unit25 = Unit.builder()
                    .title("Unit 25: N4 - BÃ€I 46")
                    .description("Tá»« vá»±ng N4 - BÃ€I 46")
                    .orderIndex(25)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit25);
        } else {
            unit25 = unitRepository.findAll().get(24);
        }
        
        List<String[]> vocabData25 = Arrays.asList(
            new String[]{"æ¸¡ã—ã¾ã™", "ã‚ãŸã—ã¾ã™", "Trao, Ä‘Æ°a cho", "Äá»™"},
            new String[]{"å¸°ã£ã¦æ¥ã¾ã™", "ã‹ãˆã£ã¦ãã¾ã™", "Trá»Ÿ vá»", "Quy Lai"},
            new String[]{"å‡ºã¾ã™", "ã§ã¾ã™", "Xuáº¥t phÃ¡t (xe bus)", "Xuáº¥t"},
            new String[]{"å±Šãã¾ã™", "ã¨ã©ãã¾ã™", "Äáº¿n (hÃ nh lÃ½ Ä‘áº¿n)", "Giá»›i"},
            new String[]{"å…¥å­¦ã—ã¾ã™", "ã«ã‚…ã†ãŒãã—ã¾ã™", "Nháº­p há»c", "Nháº­p Há»c"},
            new String[]{"å’æ¥­ã—ã¾ã™", "ãã¤ãŽã‚‡ã†ã—ã¾ã™", "Tá»‘t nghiá»‡p", "Tá»‘t Nghiá»‡p"},
            new String[]{"ç„¼ãã¾ã™", "ã‚„ãã¾ã™", "NÆ°á»›ng", "ThiÃªu"},
            new String[]{"ç„¼ã‘ã¾ã™", "ã‚„ã‘ã¾ã™", "ChÃ­n, Ä‘Æ°á»£c nÆ°á»›ng", "ThiÃªu"},
            new String[]{"ç•™å®ˆ", "ã‚‹ã™", "Váº¯ng nhÃ ", "LÆ°u Thá»§"},
            new String[]{"å®…é…ä¾¿", "ãŸãã¯ã„ã³ã‚“", "Dá»‹ch vá»¥ chuyá»ƒn phÃ¡t", "Tráº¡ch Phá»‘i Tiá»‡n"},
            new String[]{"åŽŸå› ", "ã’ã‚“ã„ã‚“", "NguyÃªn nhÃ¢n", "NguyÃªn NhÃ¢n"},
            new String[]{"ã“ã¡ã‚‰", "ã“ã¡ã‚‰", "Chá»— tÃ´i, phÃ­a tÃ´i", ""},
            new String[]{"ï½žã®æ‰€", "ï½žã®ã¨ã“ã‚", "Chá»— ~, quanh ~", "Sá»Ÿ"},
            new String[]{"åŠå¹´", "ã¯ã‚“ã¨ã—", "Ná»­a nÄƒm", "BÃ¡n NiÃªn"},
            new String[]{"ã¡ã‚‡ã†ã©", "ã¡ã‚‡ã†ã©", "Vá»«a Ä‘Ãºng lÃºc", ""},
            new String[]{"ãŸã£ãŸä»Š", "ãŸã£ãŸã„ã¾", "Vá»«a má»›i (tá»©c thÃ¬)", "Kim"},
            new String[]{"ä»Šã„ã„ã§ã™ã‹", "ã„ã¾ã„ã„ã§ã™ã‹", "BÃ¢y giá» cÃ³ phiá»n khÃ´ng?", "Kim"},
            new String[]{"ã‚¬ã‚¹ã‚µãƒ¼ãƒ“ã‚¹ã‚»ãƒ³ã‚¿ãƒ¼", "ã‚¬ã‚¹ã‚µãƒ¼ãƒ“ã‚¹ã‚»ãƒ³ã‚¿ãƒ¼", "Trung tÃ¢m dá»‹ch vá»¥ ga", ""},
            new String[]{"ã‚¬ã‚¹ãƒ¬ãƒ³ã‚¸", "ã‚¬ã‚¹ãƒ¬ãƒ³ã‚¸", "Báº¿p ga", ""},
            new String[]{"å…·åˆ", "ãã‚ã„", "Tráº¡ng thÃ¡i, tÃ¬nh tráº¡ng", "Cá»¥ Há»£p"}
        );

        if (vocabularyRepository.findByUnitId(unit25.getId()).isEmpty()) {
            for (String[] d : vocabData25) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit25)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 25 with " + vocabData25.size() + " vocabularies ======");
        }

        // Seed Unit 26 (BÃ€I 47)
        Unit unit26;
        if (unitRepository.count() < 26) {
            unit26 = Unit.builder()
                    .title("Unit 26: N4 - BÃ€I 47")
                    .description("Tá»« vá»±ng N4 - BÃ€I 47")
                    .orderIndex(26)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit26);
        } else {
            unit26 = unitRepository.findAll().get(25);
        }
        
        List<String[]> vocabData26 = Arrays.asList(
            new String[]{"é›†ã¾ã‚Šã¾ã™", "ã‚ã¤ã¾ã‚Šã¾ã™", "Táº­p trung", "Táº­p"},
            new String[]{"åˆ¥ã‚Œã¾ã™", "ã‚ã‹ã‚Œã¾ã™", "Chia tay", "Biá»‡t"},
            new String[]{"é•·ç”Ÿãã—ã¾ã™", "ãªãŒã„ãã—ã¾ã™", "Sá»‘ng thá»", "TrÆ°á»ng Sinh"},
            new String[]{"ã—ã¾ã™", "ã—ã¾ã™", "CÃ³ (Ã¢m thanh, mÃ¹i vá»‹)", ""},
            new String[]{"ã•ã—ã¾ã™", "ã•ã—ã¾ã™", "Che (Ã´)", ""},
            new String[]{"ã²ã©ã„", "ã²ã©ã„", "Tá»“i tá»‡, khá»§ng khiáº¿p", ""},
            new String[]{"æ€–ã„", "ã“ã‚ã„", "ÄÃ¡ng sá»£", "Bá»‘"},
            new String[]{"å¤©æ°—äºˆå ±", "ã¦ã‚“ãã‚ˆã»ã†", "Dá»± bÃ¡o thá»i tiáº¿t", "ThiÃªn KhÃ­ Dá»± BÃ¡o"},
            new String[]{"ç™ºè¡¨", "ã¯ã£ã´ã‚‡ã†", "PhÃ¡t biá»ƒu, cÃ´ng bá»‘", "PhÃ¡t Biá»ƒu"},
            new String[]{"å®Ÿé¨“", "ã˜ã£ã‘ã‚“", "Thá»­ nghiá»‡m, thá»±c nghiá»‡m", "Thá»±c Nghiá»‡m"},
            new String[]{"äººå£", "ã˜ã‚“ã“ã†", "DÃ¢n sá»‘", "NhÃ¢n Kháº©u"},
            new String[]{"ã«ãŠã„", "ã«ãŠã„", "MÃ¹i", ""},
            new String[]{"ç§‘å­¦", "ã‹ãŒã", "Khoa há»c", "Khoa Há»c"},
            new String[]{"åŒ»å­¦", "ã„ãŒã", "Y há»c", "Y Há»c"},
            new String[]{"æ–‡å­¦", "ã¶ã‚“ãŒã", "VÄƒn há»c", "VÄƒn Há»c"},
            new String[]{"ãƒ‘ãƒˆã‚«ãƒ¼", "ãƒ‘ãƒˆã‚«ãƒ¼", "Xe cáº£nh sÃ¡t", ""},
            new String[]{"æ•‘æ€¥è»Š", "ãã‚…ã†ãã‚…ã†ã—ã‚ƒ", "Xe cáº¥p cá»©u", "Cá»©u Cáº¥p Xa"},
            new String[]{"è³›æˆ", "ã•ã‚“ã›ã„", "TÃ¡n thÃ nh", "TÃ¡n ThÃ nh"},
            new String[]{"åå¯¾", "ã¯ã‚“ãŸã„", "Pháº£n Ä‘á»‘i", "Pháº£n Äá»‘i"},
            new String[]{"ç”·æ€§", "ã ã‚“ã›ã„", "Nam giá»›i", "Nam TÃ­nh"},
            new String[]{"å¥³æ€§", "ã˜ã‚‡ã›ã„", "Ná»¯ giá»›i", "Ná»¯ TÃ­nh"},
            new String[]{"ã©ã†ã‚‚", "ã©ã†ã‚‚", "CÃ³ váº» nhÆ°", ""},
            new String[]{"ï½žã«ã‚ˆã‚‹ã¨", "ï½žã«ã‚ˆã‚‹ã¨", "Theo nhÆ° ~", ""}
        );

        if (vocabularyRepository.findByUnitId(unit26.getId()).isEmpty()) {
            for (String[] d : vocabData26) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit26)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 26 with " + vocabData26.size() + " vocabularies ======");
        }

        // Seed Unit 27 (BÃ€I 48)
        Unit unit27;
        if (unitRepository.count() < 27) {
            unit27 = Unit.builder()
                    .title("Unit 27: N4 - BÃ€I 48")
                    .description("Tá»« vá»±ng N4 - BÃ€I 48")
                    .orderIndex(27)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit27);
        } else {
            unit27 = unitRepository.findAll().get(26);
        }
        
        List<String[]> vocabData27 = Arrays.asList(
            new String[]{"ä¸‹ã‚ã—ã¾ã™", "ãŠã‚ã—ã¾ã™", "Cho xuá»‘ng, háº¡ xuá»‘ng", "Háº¡"},
            new String[]{"å±Šã‘ã¾ã™", "ã¨ã©ã‘ã¾ã™", "Giao Ä‘áº¿n", "Giá»›i"},
            new String[]{"ä¸–è©±ã‚’ã—ã¾ã™", "ã›ã‚ã‚’ã—ã¾ã™", "ChÄƒm sÃ³c", "Tháº¿ Thoáº¡i"},
            new String[]{"éŒ²éŸ³ã—ã¾ã™", "ã‚ããŠã‚“ã—ã¾ã™", "Ghi Ã¢m", "Lá»¥c Ã‚m"},
            new String[]{"å«Œãª", "ã„ã‚„ãª", "ChÃ¡n, ghÃ©t", "Hiá»m"},
            new String[]{"å¡¾", "ã˜ã‚…ã", "CÆ¡ sá»Ÿ há»c thÃªm", "Thá»¥c"},
            new String[]{"ç”Ÿå¾’", "ã›ã„ã¨", "Há»c sinh", "Sinh Äá»“"},
            new String[]{"ãƒ•ã‚¡ã‚¤ãƒ«", "ãƒ•ã‚¡ã‚¤ãƒ«", "Tá»‡p tÃ i liá»‡u, file", ""},
            new String[]{"è‡ªç”±ã«", "ã˜ã‚†ã†ã«", "Má»™t cÃ¡ch tá»± do", "Tá»± Do"},
            new String[]{"ï½žé–“", "ï½žã‹ã‚“", "Trong khoáº£ng ~", "Gian"},
            new String[]{"ã„ã„ã“ã¨ã§ã™ã­", "ã„ã„ã“ã¨ã§ã™ã­", "Tá»‘t quÃ¡ nhá»‰", ""},
            new String[]{"ãŠå¿™ã—ã„ã§ã™ã‹", "ãŠã„ããŒã—ã„ã§ã™ã‹", "Anh/chá»‹ cÃ³ báº­n khÃ´ng?", "Mang"},
            new String[]{"å–¶æ¥­", "ãˆã„ãŽã‚‡ã†", "Kinh doanh, bÃ¡n hÃ ng", "Doanh Nghiá»‡p"},
            new String[]{"ãã‚Œã¾ã§ã«", "ãã‚Œã¾ã§ã«", "TrÆ°á»›c lÃºc Ä‘Ã³", ""},
            new String[]{"ã‹ã¾ã„ã¾ã›ã‚“", "ã‹ã¾ã„ã¾ã›ã‚“", "KhÃ´ng sao Ä‘Ã¢u", ""},
            new String[]{"æ¥½ã—ã¿ã¾ã™", "ãŸã®ã—ã¿ã¾ã™", "Táº­n hÆ°á»Ÿng", "Láº¡c"}
        );

        if (vocabularyRepository.findByUnitId(unit27.getId()).isEmpty()) {
            for (String[] d : vocabData27) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit27)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 27 with " + vocabData27.size() + " vocabularies ======");
        }

        // Seed Unit 28 (BÃ€I 49)
        Unit unit28;
        if (unitRepository.count() < 28) {
            unit28 = Unit.builder()
                    .title("Unit 28: N4 - BÃ€I 49")
                    .description("Tá»« vá»±ng N4 - BÃ€I 49")
                    .orderIndex(28)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit28);
        } else {
            unit28 = unitRepository.findAll().get(27);
        }
        
        List<String[]> vocabData28 = Arrays.asList(
            new String[]{"ã„ã‚‰ã£ã—ã‚ƒã„ã¾ã™", "ã„ã‚‰ã£ã—ã‚ƒã„ã¾ã™", "Äi, Ä‘áº¿n, á»Ÿ (tÃ´n kÃ­nh cá»§a è¡Œãã€æ¥ã‚‹ã€ã„ã‚‹)", ""},
            new String[]{"å¬ã—ä¸ŠãŒã‚Šã¾ã™", "ã‚ã—ã‚ãŒã‚Šã¾ã™", "Ä‚n, uá»‘ng (tÃ´n kÃ­nh cá»§a é£Ÿã¹ã‚‹ã€é£²ã‚€)", "Triá»‡u ThÆ°á»£ng"},
            new String[]{"ãŠã£ã—ã‚ƒã„ã¾ã™", "ãŠã£ã—ã‚ƒã„ã¾ã™", "NÃ³i (tÃ´n kÃ­nh cá»§a è¨€ã†)", ""},
            new String[]{"ãªã•ã„ã¾ã™", "ãªã•ã„ã¾ã™", "LÃ m (tÃ´n kÃ­nh cá»§a ã™ã‚‹)", ""},
            new String[]{"ã”ã‚‰ã‚“ã«ãªã‚Šã¾ã™", "ã”ã‚‰ã‚“ã«ãªã‚Šã¾ã™", "NhÃ¬n, xem (tÃ´n kÃ­nh cá»§a è¦‹ã‚‹)", ""},
            new String[]{"ã”å­˜ã˜ã§ã™", "ã”ãžã‚“ã˜ã§ã™", "Biáº¿t (tÃ´n kÃ­nh cá»§a çŸ¥ã£ã¦ã„ã‚‹)", "Tá»“n"},
            new String[]{"æŒ¨æ‹¶", "ã‚ã„ã•ã¤", "Lá»i chÃ o há»i", "Nhai Láº¡t"},
            new String[]{"æ—…é¤¨", "ã‚Šã‚‡ã‹ã‚“", "NhÃ  trá» kiá»ƒu Nháº­t", "Lá»¯ QuÃ¡n"},
            new String[]{"ãƒã‚¹åœ", "ãƒã‚¹ã¦ã„", "Báº¿n xe buÃ½t", "ÄÃ¬nh"},
            new String[]{"å¥¥æ§˜", "ãŠãã•ã¾", "Vá»£ ngÆ°á»i khÃ¡c (kÃ­nh ngá»¯)", "Ão Dáº¡ng"},
            new String[]{"ï½žæ§˜", "ï½žã•ã¾", "NgÃ i ~, Ã´ng/bÃ  ~", "Dáº¡ng"},
            new String[]{"ãŸã¾ã«", "ãŸã¾ã«", "Thi thoáº£ng", ""},
            new String[]{"ã©ãªãŸã§ã‚‚", "ã©ãªãŸã§ã‚‚", "Báº¥t cá»© ai (kÃ­nh ngá»¯)", ""},
            new String[]{"ï½žã¨ã„ã„ã¾ã™", "ï½žã¨ã„ã„ã¾ã™", "TÃªn lÃ  ~", ""},
            new String[]{"ï½žå¹´ï¼çµ„", "ï½žã­ã‚“ï¼ãã¿", "Lá»›p -, nÄƒm thá»© ~", "NiÃªn Tá»•"}
        );

        if (vocabularyRepository.findByUnitId(unit28.getId()).isEmpty()) {
            for (String[] d : vocabData28) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit28)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 28 with " + vocabData28.size() + " vocabularies ======");
        }

        // Seed Unit 29 (BÃ€I 50)
        Unit unit29;
        if (unitRepository.count() < 29) {
            unit29 = Unit.builder()
                    .title("Unit 29: N4 - BÃ€I 50")
                    .description("Tá»« vá»±ng N4 - BÃ€I 50")
                    .orderIndex(29)
                    .imageUrl("/assets/hikari_logo.png")
                    .level("N4")
                    .build();
            unitRepository.save(unit29);
        } else {
            unit29 = unitRepository.findAll().get(28);
        }
        
        List<String[]> vocabData29 = Arrays.asList(
            new String[]{"å‚ã‚Šã¾ã™", "ã¾ã„ã‚Šã¾ã™", "Äi, Ä‘áº¿n (khiÃªm nhÆ°á»ng cá»§a è¡Œãã€æ¥ã‚‹)", "Tham"},
            new String[]{"ãŠã‚Šã¾ã™", "ãŠã‚Šã¾ã™", "á»ž (khiÃªm nhÆ°á»ng cá»§a ã„ã‚‹)", ""},
            new String[]{"ã„ãŸã ãã¾ã™", "ã„ãŸã ãã¾ã™", "Ä‚n, uá»‘ng, nháº­n (khiÃªm nhÆ°á»ng cá»§a é£Ÿã¹ã‚‹ã€é£²ã‚€ã€ã‚‚ã‚‰ã†)", ""},
            new String[]{"ç”³ã—ã¾ã™", "ã‚‚ã†ã—ã¾ã™", "NÃ³i, tÃªn lÃ  (khiÃªm nhÆ°á»ng cá»§a è¨€ã†)", "ThÃ¢n"},
            new String[]{"ã„ãŸã—ã¾ã™", "ã„ãŸã—ã¾ã™", "LÃ m (khiÃªm nhÆ°á»ng cá»§a ã™ã‚‹)", ""},
            new String[]{"æ‹è¦‹ã—ã¾ã™", "ã¯ã„ã‘ã‚“ã—ã¾ã™", "NhÃ¬n, xem (khiÃªm nhÆ°á»ng cá»§a è¦‹ã‚‹)", "BÃ¡i Kiáº¿n"},
            new String[]{"å­˜ã˜ã¾ã™", "ãžã‚“ã˜ã¾ã™", "Biáº¿t (khiÃªm nhÆ°á»ng cá»§a çŸ¥ã‚‹)", "Tá»“n"},
            new String[]{"ä¼ºã„ã¾ã™", "ã†ã‹ãŒã„ã¾ã™", "Há»i, nghe, Ä‘áº¿n thÄƒm (khiÃªm nhÆ°á»ng cá»§a èžãã€è¡Œã)", "Tá»©"},
            new String[]{"ãŠç›®ã«ã‹ã‹ã‚Šã¾ã™", "ãŠã‚ã«ã‹ã‹ã‚Šã¾ã™", "Gáº·p (khiÃªm nhÆ°á»ng cá»§a ä¼šã†)", "Má»¥c"},
            new String[]{"ã„ã‚Œã¾ã™", "ã„ã‚Œã¾ã™", "Pha (cÃ  phÃª, trÃ )", ""},
            new String[]{"ç”¨æ„ã—ã¾ã™", "ã‚ˆã†ã„ã—ã¾ã™", "Chuáº©n bá»‹", "Dá»¥ng Ã"},
            new String[]{"ç§", "ã‚ãŸãã—", "TÃ´i (khiÃªm nhÆ°á»ng cá»§a ã‚ãŸã—)", "TÆ°"},
            new String[]{"ã‚¬ã‚¤ãƒ‰", "ã‚¬ã‚¤ãƒ‰", "HÆ°á»›ng dáº«n viÃªn", ""},
            new String[]{"ãƒ¡ãƒ¼ãƒ«ã‚¢ãƒ‰ãƒ¬ã‚¹", "ãƒ¡ãƒ¼ãƒ«ã‚¢ãƒ‰ãƒ¬ã‚¹", "Äá»‹a chá»‰ email", ""},
            new String[]{"ã‚¹ã‚±ã‚¸ãƒ¥ãƒ¼ãƒ«", "ã‚¹ã‚±ã‚¸ãƒ¥ãƒ¼ãƒ«", "Lá»‹ch trÃ¬nh", ""},
            new String[]{"ã•æ¥é€±", "ã•ã‚‰ã„ã—ã‚…ã†", "Tuáº§n sau ná»¯a", "Lai Chu"},
            new String[]{"ã•æ¥æœˆ", "ã•ã‚‰ã„ã’ã¤", "ThÃ¡ng sau ná»¯a", "Lai Nguyá»‡t"},
            new String[]{"åˆã‚ã«", "ã¯ã˜ã‚ã«", "Äáº§u tiÃªn", "SÆ¡"},
            new String[]{"ç·Šå¼µã—ã¾ã™", "ãã‚“ã¡ã‚‡ã†ã—ã¾ã™", "CÄƒng tháº³ng, há»“i há»™p", "Kháº©n TrÆ°Æ¡ng"},
            new String[]{"è³žé‡‘", "ã—ã‚‡ã†ãã‚“", "Tiá»n thÆ°á»Ÿng", "ThÆ°á»Ÿng Kim"},
            new String[]{"ãã‚Šã‚“", "ãã‚Šã‚“", "HÆ°Æ¡u cao cá»•", ""},
            new String[]{"ã“ã‚", "ã“ã‚", "Há»“i, thá»i", ""},
            new String[]{"ã‹ãªã„ã¾ã™", "ã‹ãªã„ã¾ã™", "Trá»Ÿ thÃ nh hiá»‡n thá»±c (Æ°á»›c mÆ¡)", ""}
        );

        if (vocabularyRepository.findByUnitId(unit29.getId()).isEmpty()) {
            for (String[] d : vocabData29) {
                Vocabulary voc = Vocabulary.builder()
                        .unit(unit29)
                        .kanji(d[0])
                        .hiragana(d[1])
                        .romaji(d[1]) 
                        .meaning(d[2])
                        .sinoVietnamese(d[3])
                        .build();
                vocabularyRepository.save(voc);
            }
            System.out.println("====== Seeded Unit 29 with " + vocabData29.size() + " vocabularies ======");
        }







        // Generate questions for any unit that has vocabulary but no questions
        for (Unit unit : unitRepository.findAll()) {
            if (questionRepository.findByUnitId(unit.getId()).isEmpty()) {
                seedQuestionsForUnit(unit);
                System.out.println("====== Seeded Questions for Unit " + unit.getId() + " ======");
            }
        }

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
                ? current.getKanji() + "ï¼ˆ" + current.getHiragana() + "ï¼‰" 
                : current.getHiragana();
            options.add(correctText);

            for (Vocabulary wrong : wrongOptions) {
                String wrongText = wrong.getKanji() != null && !wrong.getKanji().isEmpty() 
                    ? wrong.getKanji() + "ï¼ˆ" + wrong.getHiragana() + "ï¼‰" 
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




