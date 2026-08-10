# Hướng Dẫn Triển Khai (Deployment Guide)

Tài liệu này hướng dẫn chi tiết các bước đưa dự án **Nihongo Quizz** lên mạng hoàn toàn miễn phí.
Mục tiêu cuối cùng là laptop của bạn có thể tắt đi, nhưng mọi người trên thế giới vẫn truy cập được vào web qua domain `https://nihongo.click`.

## Bước 1: Tạo Database PostgreSQL trên Supabase
1. Đăng ký/Đăng nhập tài khoản tại [Supabase](https://supabase.com).
2. Nhấn **New Project**, chọn một Organization và đặt tên cho dự án (ví dụ: `nihongo-quizz`). Điền một mật khẩu Database đủ mạnh và chọn vùng (Region) gần Việt Nam nhất (Singapore).
3. Đợi vài phút để Database được khởi tạo.
4. Sau khi khởi tạo xong, vào phần **Settings -> Database -> Connection string -> URI**.
5. Copy URL kết nối, trông nó sẽ tương tự như sau (nhớ thay đổi mật khẩu bằng mật khẩu bạn đã tạo ở bước 2):
   `postgresql://postgres.xxx:YOUR_PASSWORD@aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres`

---

## Bước 2: Triển Khai Backend (Spring Boot) lên Render
1. Tạo một tài khoản trên [Render](https://render.com). Liên kết tài khoản GitHub của bạn với Render.
2. Trên GitHub, bạn cần đẩy (push) toàn bộ thư mục code hiện tại lên một Repository mới.
3. Trên giao diện Render, chọn **New -> Web Service**.
4. Chọn **Build and deploy from a Git repository**, rồi kết nối với Repository chứa dự án của bạn trên GitHub.
5. Điền các cấu hình sau trên Render:
   - **Name**: `nihongo-backend`
   - **Language**: `Java`
   - **Branch**: `main` (hoặc nhánh bạn đang push lên)
   - **Root Directory**: `backend` (Rất quan trọng, vì Backend của bạn nằm trong folder này).
   - **Build Command**: `./gradlew clean build -x test`
   - **Start Command**: `java -jar build/libs/backend-0.0.1-SNAPSHOT.jar`
   - **Instance Type**: `Free`
6. Cuộn xuống phần **Environment Variables**, thêm các biến sau:
   - `DB_URL`: Điền URI kết nối lấy từ Bước 1, nhưng thay chữ `postgresql://` ở đầu bằng `jdbc:postgresql://`.
   - `DB_USERNAME`: `postgres`
   - `DB_PASSWORD`: Mật khẩu Supabase của bạn.
   - `JWT_SECRET`: (Tạo một chuỗi ngẫu nhiên bí mật và dài, ví dụ: `djas823nmn12u39d8ansd20912m312ds12`).
   - `CORS_ALLOWED_ORIGINS`: `https://nihongo.click,https://www.nihongo.click`
   - `MAIL_USERNAME`: Email của bạn (để gửi mã OTP).
   - `MAIL_PASSWORD`: App Password của email.
7. Nhấn **Create Web Service**. Đợi khoảng 5-10 phút để Render tự động build và start.
8. Sau khi xong, Render sẽ cấp cho bạn một URL, ví dụ: `https://nihongo-backend.onrender.com`. Hãy copy URL này.

---

## Bước 3: Triển Khai Frontend (React) lên Cloudflare Pages
1. Đăng nhập vào [Cloudflare](https://dash.cloudflare.com), vào mục **Workers & Pages -> Pages -> Connect to Git**.
2. Liên kết tài khoản GitHub và chọn Repository giống ở Bước 2.
3. Điền các cấu hình sau:
   - **Project name**: `nihongo-quizz`
   - **Production branch**: `main`
   - **Framework preset**: `None`
   - **Build command**: `npm run build`
   - **Build output directory**: `dist`
   - **Root directory (Advanced)**: `/frontend/app`
4. Mở phần **Environment variables (advanced)** và thêm biến sau:
   - Variable name: `VITE_API_URL`
   - Value: `https://<URL_RENDER_CUA_BAN_O_BUOC_2>/api`
   *(Chú ý: Thay bằng URL bạn copy từ Render ở cuối Bước 2 và phải có đuôi `/api`)*
5. Nhấn **Save and Deploy**. Cloudflare sẽ tự động build code React của bạn trong khoảng 1-2 phút.
6. Sau khi xong, hệ thống sẽ cấp một link dạng `.pages.dev`.

---

## Bước 4: Trỏ Tên Miền `nihongo.click` về Cloudflare
1. Vào tab **Custom Domains** trong dự án Cloudflare Pages vừa tạo.
2. Nhấn **Set up a custom domain**, điền `nihongo.click` (Đảm bảo tên miền này đã được quản lý DNS trên Cloudflare hoặc trỏ NS về Cloudflare từ trước).
3. Cloudflare sẽ tự động cấu hình bản ghi DNS để kết nối. Bạn cũng có thể thiết lập thêm tên miền `www.nihongo.click` bằng cách làm tương tự.

---

## Bước 5: Kiểm Tra Cuối Cùng
1. Truy cập `https://nihongo.click`.
2. Kiểm tra lại mọi chức năng: Đăng nhập/Đăng ký. Lúc này Backend Render sẽ kết nối vào Supabase, nếu Database mới tinh, hàm Seeder sẽ tự động tạo đủ bảng và thêm Unit 1, 2, 3, 4 ngay trong lần khởi động đầu tiên.
3. Làm Quiz, học từ vựng.
4. Mọi thứ đã hoàn tất! Laptop của bạn lúc này có thể tắt hoàn toàn. Mọi người đều có thể truy cập hệ thống.
