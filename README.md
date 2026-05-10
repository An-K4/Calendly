# 📱 Android Calendar App

Ứng dụng lịch cá nhân viết bằng **Java + XML + MVP Architecture**, hỗ trợ import file `.ics`, âm lịch, thông báo, và nhiều chế độ xem.

---

<details>
<summary>📚 Lộ Trình Học – Android Calendar App (Java + XML + MVP)</summary>

<br>

> **Đối tượng:** Đã biết Java/XML cơ bản, đã quên nhiều, gần như học lại từ đầu.  
> **Mục tiêu:** Đủ kiến thức để xây dựng hoàn chỉnh ứng dụng lịch trong ~1.5 tháng.

---

## GIAI ĐOẠN 0 – Ôn lại nền tảng (Tuần 1)

### 0.1 – Ôn Java cho Android

**Mục tiêu:** Nắm lại OOP, Collections, Interface, Lambda cơ bản.

| Nguồn | Loại | Link |
|---|---|---|
| Telusko – Java Full Course | Video (YouTube) | https://www.youtube.com/watch?v=eIrMbAQSU34 |
| Bro Code – Java (ngắn gọn) | Video (YouTube) | https://www.youtube.com/watch?v=xk4_1vDrzzo |
| W3Schools Java | Bài viết/Reference | https://www.w3schools.com/java/ |

**Cần ôn nhanh:** `interface`, `abstract class`, `ArrayList`, `HashMap`, `Comparator`, `try-catch`, Lambda expression (Java 8+).

---

### 0.2 – Ôn lại Android cơ bản

**Mục tiêu:** Nhớ lại Activity/Fragment lifecycle, View, Layout, Intent, RecyclerView.

| Nguồn | Loại | Link |
|---|---|---|
| Philipp Lackner – Android Basics Crashcourse | Video (YouTube) | https://www.youtube.com/watch?v=FjrKMcnKahY |
| Android Developers – Guides | Tài liệu chính thức | https://developer.android.com/guide |
| CodeWithMitch – Android RecyclerView | Video (YouTube) | https://www.youtube.com/watch?v=Vyqz_-sJGFk |

**Cần nhớ lại:**
- Activity/Fragment lifecycle (onCreate, onStart, onResume...)
- ViewBinding (thay thế `findViewById`)
- RecyclerView + Adapter + ViewHolder
- Intent (chuyển màn, truyền data)

---

## GIAI ĐOẠN 1 – Kiến trúc MVP (Tuần 1–2)

### 1.1 – Hiểu MVP Pattern

MVP = Model – View – Presenter. Đây là xương sống toàn bộ dự án.

| Nguồn | Loại | Link |
|---|---|---|
| MindOrks – MVP Architecture | Bài viết | https://mindorks.com/course/android-mvp-introduction |
| Android MVP Sample (Google) | Code mẫu | https://github.com/android/architecture-samples/tree/todo-mvp |
| Vogella – Android MVP | Bài viết chi tiết | https://www.vogella.com/tutorials/AndroidArchitecture/article.html |

**Khái niệm cần nắm:**
```
View (Activity/Fragment)
  └── gọi Presenter khi có event (user click, load data...)
  
Presenter
  └── xử lý logic
  └── gọi Model để lấy/lưu data
  └── trả kết quả về View qua Interface

Model
  └── đọc/ghi database, file, API
  └── không biết View tồn tại
```

**Ví dụ interface cơ bản:**
```java
// Contract định nghĩa quan hệ View–Presenter
public interface CalendarContract {
    interface View {
        void showEvents(List<Event> events);
        void showError(String message);
    }
    interface Presenter {
        void loadEventsForDay(LocalDate date);
        void addEvent(Event event);
        void onDestroy();
    }
}
```

---

## GIAI ĐOẠN 2 – Database & Lưu trữ (Tuần 2)

### 2.1 – Room Database

Room là thư viện ORM chính thức của Android, dùng để lưu sự kiện.

| Nguồn | Loại | Link |
|---|---|---|
| Android Developers – Room | Tài liệu chính thức | https://developer.android.com/training/data-storage/room |
| Coding in Flow – Room Database | Video (YouTube) | https://www.youtube.com/watch?v=lwAvI3WDXBY |
| Philipp Lackner – Room + ViewModel | Video (YouTube) | https://www.youtube.com/watch?v=bOd3wO0uFr8 |

**Cần học:**
- `@Entity`, `@Dao`, `@Database`
- CRUD: insert, update, delete, query
- Chạy Room trên background thread (AsyncTask hoặc Executors)

---

### 2.2 – Đọc file ICS (iCalendar)

File `.ics` tuân theo chuẩn RFC 5545. Cần parser để đọc.

| Nguồn | Loại | Link |
|---|---|---|
| iCal4j – Java Library | Thư viện | https://github.com/ical4j/ical4j |
| RFC 5545 – iCalendar Spec | Tài liệu chuẩn | https://datatracker.ietf.org/doc/html/rfc5545 |
| Bài viết parse ICS thủ công | Bài viết | https://www.baeldung.com/java-ical4j |

**Cấu trúc file .ics cần biết:**
```
BEGIN:VCALENDAR
  BEGIN:VEVENT
    DTSTART:20240515T090000Z
    DTEND:20240515T100000Z
    SUMMARY:Họp nhóm
    LOCATION:Phòng A
    ORGANIZER:CN=Nguyễn Văn A
    DESCRIPTION:Ghi chú sự kiện
    VALARM → TRIGGER:-PT30M  (thông báo trước 30 phút)
  END:VEVENT
END:VCALENDAR
```

---

## GIAI ĐOẠN 3 – UI Calendar & Date/Time (Tuần 2–3)

### 3.1 – Xây dựng Calendar View

| Nguồn | Loại | Link |
|---|---|---|
| ThaiCalendar / MaterialCalendarView | Thư viện | https://github.com/prolificinteractive/material-calendarview |
| Kishan Donga – Custom Calendar Android | Video (YouTube) | https://www.youtube.com/watch?v=Ba0Q-cK7sC0 |
| Android Developers – Date & Time | Tài liệu | https://developer.android.com/reference/java/time/LocalDate |

**Các chế độ xem cần implement:**
- **Ngày:** hiển thị danh sách sự kiện trong ngày bằng RecyclerView
- **Tháng:** grid calendar, chấm màu trên ngày có sự kiện
- **Năm:** grid 12 tháng mini

---

### 3.2 – Java Date/Time API

Dùng `java.time.*` (Java 8+, available từ API 26, hoặc dùng desugaring cho API thấp hơn).

| Nguồn | Loại | Link |
|---|---|---|
| Baeldung – Java LocalDate | Bài viết | https://www.baeldung.com/java-8-date-time-intro |
| Android Developers – Java 8+ | Tài liệu | https://developer.android.com/studio/write/java8-support |

```java
// Kích hoạt Java 8 date/time cho API < 26
android {
    compileOptions {
        coreLibraryDesugaringEnabled true
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
dependencies {
    coreLibraryDesugaring 'com.android.tools.build:desugaring:2.0.4'
}
```

---

### 3.3 – Âm lịch (Lunar Calendar)

| Nguồn | Loại | Link |
|---|---|---|
| LunarCalendar Java library | Thư viện / Code mẫu | https://github.com/haiminhtran810/Vietnamese-Lunar-Calendar |
| Convert Dương → Âm (thuật toán) | Bài viết | https://www.informatik.uni-leipzig.de/~duc/amlich/amlich-hnd.js.txt |

**Cách dùng:** Chuyển đổi `LocalDate` → ngày âm lịch và hiển thị nhỏ bên dưới ngày dương.

---

## GIAI ĐOẠN 4 – Thông báo (Tuần 3)

### 4.1 – Android Notifications + AlarmManager

| Nguồn | Loại | Link |
|---|---|---|
| Android Developers – Notifications | Tài liệu chính thức | https://developer.android.com/develop/ui/views/notifications |
| Android Developers – AlarmManager | Tài liệu chính thức | https://developer.android.com/training/scheduling/alarms |
| Coding in Flow – Notifications | Video (YouTube) | https://www.youtube.com/watch?v=tTbd1Mfi-Sk |

**Flow thông báo:**
```
Tạo sự kiện → lưu thời gian thông báo 
  → AlarmManager.setExactAndAllowWhileIdle() 
  → BroadcastReceiver nhận alarm 
  → NotificationManager.notify()
```

**Lưu ý:** Android 12+ yêu cầu permission `SCHEDULE_EXACT_ALARM`.

---

## GIAI ĐOẠN 5 – Tính năng nâng cao (Tuần 3–4)

### 5.1 – File Picker (chọn file .ics)

```java
// ActivityResultLauncher thay thế startActivityForResult
ActivityResultLauncher<String[]> filePickerLauncher = registerForActivityResult(
    new ActivityResultContracts.OpenDocument(),
    uri -> presenter.importIcsFile(uri)
);
filePickerLauncher.launch(new String[]{"text/calendar"});
```

| Nguồn | Loại | Link |
|---|---|---|
| Android Developers – Open files | Tài liệu | https://developer.android.com/training/data-storage/shared/documents-files |

---

### 5.2 – ViewPager2 (chuyển chế độ xem)

| Nguồn | Loại | Link |
|---|---|---|
| Coding in Flow – ViewPager2 | Video (YouTube) | https://www.youtube.com/watch?v=Ul3Yq9ykOaI |
| Android Developers – ViewPager2 | Tài liệu | https://developer.android.com/guide/navigation/navigation-swipe-view-2 |

---

## GIAI ĐOẠN 6 – Dependency Injection & Chất lượng code (Tùy chọn / Tuần 4)

### 6.1 – Dagger2 / Hilt (Tùy chọn)

| Nguồn | Loại | Link |
|---|---|---|
| Philipp Lackner – Hilt DI | Video (YouTube) | https://www.youtube.com/watch?v=bbMsuI2p1DQ |

---

## 📖 Sách Tham Khảo

| Sách | Ghi chú |
|---|---|
| *Android Programming: The Big Nerd Ranch Guide* (5th Ed.) | Tổng quan Android toàn diện, ví dụ Java |
| *Clean Architecture* – Robert C. Martin | Hiểu sâu hơn về tách biệt layer (Model, Presenter, View) |
| *Head First Design Patterns* | Nền tảng pattern cho MVP |

---

## 🎯 Thứ tự học tối ưu theo tuần

| Tuần | Nội dung |
|---|---|
| Tuần 1 | Ôn Java → Ôn Activity/Fragment/RecyclerView → Học MVP pattern |
| Tuần 2 | Room Database → ICS parser (iCal4j) → Java Date/Time |
| Tuần 3 | Calendar UI → Lunar calendar → Notifications + AlarmManager |
| Tuần 4 | File picker → Filter theo file → ViewPager2 → Hoàn thiện |
| Tuần 5–6 | Kiểm thử, sửa bug, polish UI |

> 💡 **Tip:** Không cần học hết lý thuyết rồi mới làm. Học đến đâu, code luôn đến đó theo cấu trúc dự án. Mỗi tính năng là một bài học thực hành.

</details>

---

<details>
<summary>🏗️ Cấu Trúc & Thiết Kế Dự Án – Android Calendar App</summary>

<br>

> Tech stack: Java + XML + MVP Architecture  
> Min SDK: 26 (Android 8.0) — để dùng `java.time.*` không cần desugaring

---

## 1. Cấu trúc thư mục

```
CalendarApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/yourname/calendarapp/
│   │       │   │
│   │       │   ├── data/                         # LAYER: Model
│   │       │   │   ├── db/
│   │       │   │   │   ├── AppDatabase.java       # Room Database singleton
│   │       │   │   │   ├── EventDao.java          # Data Access Object
│   │       │   │   │   └── CalendarSourceDao.java # Quản lý file ICS đã nhập
│   │       │   │   ├── model/
│   │       │   │   │   ├── Event.java             # @Entity Room – sự kiện
│   │       │   │   │   └── CalendarSource.java    # @Entity – nguồn lịch (1 file .ics)
│   │       │   │   ├── parser/
│   │       │   │   │   └── IcsParser.java         # Đọc & parse file .ics
│   │       │   │   ├── repository/
│   │       │   │   │   ├── EventRepository.java   # Giao tiếp DB + Parser
│   │       │   │   │   └── IEventRepository.java  # Interface để Presenter gọi
│   │       │   │   └── notification/
│   │       │   │       ├── AlarmScheduler.java    # Đặt/hủy AlarmManager
│   │       │   │       └── ReminderReceiver.java  # BroadcastReceiver nhận alarm
│   │       │   │
│   │       │   ├── ui/                            # LAYER: View + Presenter
│   │       │   │   │
│   │       │   │   ├── main/                      # Màn hình chính (container)
│   │       │   │   │   ├── MainActivity.java
│   │       │   │   │   └── MainContract.java
│   │       │   │   │
│   │       │   │   ├── calendar/                  # Xem lịch (ngày/tháng/năm)
│   │       │   │   │   ├── CalendarFragment.java
│   │       │   │   │   ├── CalendarPresenter.java
│   │       │   │   │   └── CalendarContract.java
│   │       │   │   │
│   │       │   │   ├── agenda/                    # Xem sự kiện theo năm (Agenda)
│   │       │   │   │   ├── AgendaFragment.java
│   │       │   │   │   ├── AgendaPresenter.java
│   │       │   │   │   ├── AgendaContract.java
│   │       │   │   │   ├── AgendaAdapter.java      # Multiple ViewType: header ngày + event
│   │       │   │   │   └── YearSelectorAdapter.java # HorizontalRecyclerView chọn năm
│   │       │   │   │
│   │       │   │   ├── event/
│   │       │   │   │   ├── detail/                # Chi tiết sự kiện
│   │       │   │   │   │   ├── EventDetailActivity.java
│   │       │   │   │   │   ├── EventDetailPresenter.java
│   │       │   │   │   │   └── EventDetailContract.java
│   │       │   │   │   └── add/                   # Thêm sự kiện mới
│   │       │   │   │       ├── AddEventActivity.java
│   │       │   │   │       ├── AddEventPresenter.java
│   │       │   │   │       └── AddEventContract.java
│   │       │   │   │
│   │       │   │   └── sources/                   # Quản lý file ICS đã nhập
│   │       │   │       ├── SourceManagerFragment.java
│   │       │   │       ├── SourceManagerPresenter.java
│   │       │   │       ├── SourceManagerContract.java
│   │       │   │       └── SourceAdapter.java
│   │       │   │
│   │       │   └── util/
│   │       │       ├── LunarCalendarUtil.java     # Chuyển đổi âm lịch
│   │       │       ├── DateUtils.java             # Format ngày giờ
│   │       │       └── NotificationHelper.java    # Tạo Notification Channel
│   │       │
│   │       └── res/
│   │           ├── layout/
│   │           │   ├── activity_main.xml
│   │           │   ├── activity_add_event.xml
│   │           │   ├── activity_event_detail.xml
│   │           │   ├── fragment_calendar.xml
│   │           │   ├── fragment_day_view.xml
│   │           │   ├── fragment_source_manager.xml
│   │           │   ├── item_event.xml             # RecyclerView item – sự kiện
│   │           │   └── item_source.xml            # RecyclerView item – file lịch
│   │           ├── drawable/
│   │           ├── values/
│   │           │   ├── colors.xml
│   │           │   ├── strings.xml
│   │           │   └── themes.xml
│   │           └── xml/
│   │               └── file_paths.xml             # FileProvider paths
│   │
│   └── build.gradle
└── build.gradle
```

---

## 2. Thiết kế Model (Data Layer)

### Event.java — Entity chính

```java
@Entity(tableName = "events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String uid;              // UID từ .ics (tránh import trùng)
    public String title;            // SUMMARY
    public long startTimeMillis;    // DTSTART → milliseconds
    public long endTimeMillis;      // DTEND
    public String location;         // LOCATION
    public String organizer;        // ORGANIZER CN=...
    public String description;      // DESCRIPTION / ghi chú
    public long reminderMinutes;    // VALARM TRIGGER (phút trước khi bắt đầu)
    public int sourceId;            // FK → CalendarSource.id (0 = tạo tay)
    public boolean isAllDay;        // Sự kiện cả ngày
}
```

### CalendarSource.java — Nguồn lịch (.ics file)

```java
@Entity(tableName = "calendar_sources")
public class CalendarSource {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String displayName;      // Tên hiển thị
    public String filePath;         // URI string của file gốc
    public String color;            // Hex color, ví dụ "#FF5722"
    public boolean isVisible;       // Filter on/off
    public long importedAt;         // Timestamp nhập lần cuối
}
```

---

## 3. MVP Contract Pattern

```java
// CalendarContract.java
public interface CalendarContract {

    interface View {
        void showEventsOnDate(LocalDate date, List<Event> events);
        void highlightDatesWithEvents(Set<LocalDate> dates);
        void showLunarDate(LocalDate solar, String lunarText);
        void navigateToDay(LocalDate date);
        void showError(String message);
    }

    interface Presenter {
        void onDateSelected(LocalDate date);
        void onMonthChanged(YearMonth month);
        void onTodayClicked();
        void onViewModeChanged(ViewMode mode);
        void onDestroy();
    }

    enum ViewMode { DAY, MONTH, YEAR }
}
```

---

## 4. Luồng dữ liệu MVP

```
[User tương tác]
      │
      ▼
[View – Fragment/Activity]
      │  gọi presenter.method()
      ▼
[Presenter]
      │  gọi repository.method() trên background thread
      ▼
[Repository]
      │  đọc Room DB hoặc parse .ics
      ▼
[Presenter nhận data]
      │  post về Main thread
      ▼
[View – view.showXxx()]
      │
      ▼
[UI cập nhật]
```

**Background threading:** Dùng `ExecutorService` + `Handler(Looper.getMainLooper())`:

```java
executor.execute(() -> {
    List<Event> events = repository.getEventsByDate(date);
    mainHandler.post(() -> {
        if (view != null) view.showEventsOnDate(date, events);
    });
});
```

---

## 5. Luồng Import File ICS

```
User chọn file .ics (FilePicker)
      │
      ▼
SourceManagerPresenter.importFile(uri)
      │
      ▼
IcsParser.parse(uri)  →  List<Event> + CalendarSource
      │
      ▼
EventRepository.saveAll(events, source)
      │  ─ insert CalendarSource → lấy sourceId
      │  ─ insert tất cả Event với sourceId tương ứng
      │  ─ bỏ qua Event trùng UID
      │
      ▼
AlarmScheduler.scheduleAll(events)
      │
      ▼
View cập nhật danh sách nguồn + calendar
```

---

## 6. Luồng Thông Báo

```
Event được lưu (tạo tay hoặc import)
      │
      ▼
AlarmScheduler.schedule(event)
      │  AlarmManager.setExactAndAllowWhileIdle(
      │      startTimeMillis - reminderMinutes * 60_000
      │  )
      │
      ▼
[Đến giờ alarm]
ReminderReceiver.onReceive()
      │
      ▼
NotificationHelper.showNotification(event)
```

---

## 7. Dependencies (build.gradle)

```gradle
dependencies {
    // Room
    implementation "androidx.room:room-runtime:2.6.1"
    annotationProcessor "androidx.room:room-compiler:2.6.1"

    // ViewPager2
    implementation "androidx.viewpager2:viewpager2:1.0.0"

    // MaterialCalendarView
    implementation 'com.prolificinteractive:material-calendarview:1.4.3'

    // iCal4j
    implementation 'org.mnode.ical4j:ical4j:3.2.14'

    // Material Design Components
    implementation 'com.google.android.material:material:1.11.0'
}
```

---

## 8. Màn hình & Navigation

```
MainActivity (BottomNavigationView)
├── Tab 1: CalendarFragment          ← Xem lịch chính (ngày/tháng/năm)
│     └── [click ngày] → AgendaFragment scroll đến ngày đó
├── Tab 2: AgendaFragment            ← Xem toàn bộ sự kiện theo năm (agenda view)
│     └── [click sự kiện] → EventDetailActivity
├── Tab 3: SourceManagerFragment     ← Quản lý file .ics đã nhập
│     └── [+ import] → FilePicker → import flow
└── FAB (+): AddEventActivity        ← Tạo sự kiện mới
```

---

## 9. Màu sắc & Theme

```xml
<color name="primary">#1565C0</color>
<color name="primary_light">#E3F2FD</color>
<color name="accent">#FF6F00</color>
<color name="event_personal">#4CAF50</color>
<color name="event_work">#2196F3</color>
<color name="lunar_text">#9E9E9E</color>
```

---

## 10. Permissions (AndroidManifest.xml)

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32"/>
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```

> 📌 **Nguyên tắc thiết kế:**
> - Presenter **không import** bất kỳ class Android nào — chỉ Java thuần
> - View **không chứa logic** — chỉ gọi Presenter và cập nhật UI
> - Repository là cầu nối duy nhất giữa Presenter và dữ liệu thô

</details>

---

<details>
<summary>⏱️ Timeline Tiến Độ – Android Calendar App</summary>

<br>

> **Tổng thời gian:** 6 tuần (tối đa 1.5 tháng)  
> **Giả định:** ~3–4 giờ học/code mỗi ngày  
> **Chiến lược:** Build vertical slice — mỗi tuần ra được 1 tính năng chạy được, không học lý thuyết rồi mới code

---

## Tổng quan

| Tuần | Tên giai đoạn | Kết quả cuối tuần |
|---|---|---|
| Tuần 1 | Setup + Ôn nền tảng + MVP skeleton | App chạy được với 1 màn hình, MVP hoạt động |
| Tuần 2 | Room DB + Thêm sự kiện + Xem ngày | Tạo sự kiện, lưu DB, xem sự kiện theo ngày |
| Tuần 3 | Xem tháng + Xem năm + Nút "Hôm nay" | Đủ 3 chế độ xem, chuyển đổi mượt |
| Tuần 4 | Import ICS + Filter theo nguồn | Import file .ics, bật/tắt nguồn lịch |
| Tuần 5 | Âm lịch + Thông báo | Hiển thị ngày âm, alarm đúng giờ |
| Tuần 6 | Chi tiết sự kiện + Polish + Bug fix | App hoàn chỉnh, UI đẹp, ổn định |

---

## TUẦN 1 – Nền tảng & Khung Dự Án

**Mục tiêu:** Setup project, ôn nhanh Android, dựng MVP skeleton chạy được.

### Ngày 1–2: Setup & Ôn tập
- [✓] Tạo Android project (Java, minSdk 26)
- [✓] Bật ViewBinding trong `build.gradle`
- [✓] Thêm dependencies: Room, Material, ViewPager2, iCal4j
- [✓] Xem lại: Activity lifecycle, Fragment, RecyclerView nếu cần ôn
- [✓] Đọc: MVP pattern – hiểu rõ Contract, View, Presenter

### Ngày 3–4: Dựng MVP skeleton
- [✓] Tạo `MainActivity` với `BottomNavigationView` (3 tab: Lịch, Nguồn, +)
- [✓] Tạo `CalendarFragment` (placeholder) theo MVP
- [✓] Kiểm tra: Fragment hiển thị, Presenter gọi được, không crash

### Ngày 5–7: Room DB + Model
- [✓] Tạo `Event.java` (@Entity)
- [✓] Tạo `CalendarSource.java` (@Entity)
- [✓] Tạo `EventDao.java` với các query cơ bản
- [✓] Tạo `AppDatabase.java` (Room singleton)
- [✓] Tạo `EventRepository.java`
- [✓] Test insert/query bằng log

**✅ Checkpoint tuần 1:** App khởi động, navigation hoạt động, DB tạo thành công.

---

## TUẦN 2 – Thêm Sự Kiện & Xem Theo Ngày

**Mục tiêu:** Tạo sự kiện từ UI, lưu DB, hiển thị danh sách theo ngày.

### Ngày 8–10: Màn hình Thêm Sự Kiện
- [✓] Tạo `AddEventActivity` + `AddEventContract` + `AddEventPresenter`
- [✓] Layout với TextInput, DateTimePicker, Spinner thông báo, Button Lưu
- [✓] Presenter gọi `repository.saveEvent(event)` trên background thread

### Ngày 11–13: Màn hình Agenda (Xem sự kiện theo năm)
- [ ] Tạo `AgendaFragment` + `AgendaContract` + `AgendaPresenter`
- [ ] Layout `fragment_agenda.xml`:
    - Toolbar: nút ◀ ▶ + HorizontalRecyclerView chọn năm (từ `năm hiện tại - 5` đến `năm sự kiện xa nhất + 5`)
    - RecyclerView dọc chính: 2 ViewType — `TYPE_HEADER` (ngày) và `TYPE_EVENT` (sự kiện)
- [ ] `AgendaAdapter.java` với Multiple ViewType + `AgendaListItem` (abstract), `AgendaHeaderItem`, `AgendaEventItem`
- [ ] `YearSelectorAdapter.java` + `item_year.xml`
- [ ] `item_agenda_header.xml` + `item_agenda_event.xml`
- [ ] Presenter: group event theo ngày (TreeMap), build danh sách phẳng, tính range năm từ DB
- [ ] Gắn vào tab tương ứng trong `MainActivity`

### Ngày 14: Kết nối & test
- [ ] Tạo sự kiện → xem trong DayView → xác nhận data đúng
- [ ] Xử lý case: không có sự kiện (empty state)

**✅ Checkpoint tuần 2:** Tạo sự kiện, lưu, xem được theo ngày.

---

## TUẦN 3 – Calendar View (Tháng + Năm + Hôm nay)

**Mục tiêu:** Đủ 3 chế độ xem, nút về hôm nay.

### Ngày 15–17: Xem Tháng
- [ ] Tích hợp `MaterialCalendarView` vào `CalendarFragment`
- [ ] Hiển thị chấm màu (decorator) trên ngày có sự kiện
- [ ] Click ngày → load sự kiện ngày đó vào DayView bên dưới

### Ngày 18–19: Xem Năm
- [ ] Tạo `YearViewFragment`: grid 12 mini calendar
- [ ] Click tháng → chuyển sang Month View của tháng đó

### Ngày 20–21: Chuyển chế độ xem & nút Hôm nay
- [ ] `ViewPager2` hoặc `TabLayout` để chuyển Day / Month / Year
- [ ] Nút **"Hôm nay"** trên toolbar: về `LocalDate.now()`, highlight ngày hiện tại

**✅ Checkpoint tuần 3:** Xem lịch 3 chế độ, nút hôm nay hoạt động.

---

## TUẦN 4 – Import File ICS & Filter Nguồn

**Mục tiêu:** Import file .ics, hiển thị nhiều nguồn lịch, filter bật/tắt.

### Ngày 22–24: Import ICS
- [ ] Tạo `IcsParser.java` dùng iCal4j
- [ ] `SourceManagerFragment` + MVP
- [ ] Nút Import → `ActivityResultLauncher` mở file picker
- [ ] Tự động bỏ qua event trùng UID

### Ngày 25–26: Giao diện quản lý nguồn
- [ ] RecyclerView danh sách file đã nhập: tên, số sự kiện, màu, toggle
- [ ] Toggle bật/tắt → cập nhật `isVisible` trong DB
- [ ] Xóa nguồn → xóa cả Event liên quan

### Ngày 27–28: Filter trên Calendar
- [ ] `EventRepository` query thêm điều kiện `source.isVisible = 1`
- [ ] Màu event trên RecyclerView theo màu nguồn

**✅ Checkpoint tuần 4:** Import nhiều file .ics, filter nguồn, calendar phản ánh đúng.

---

## TUẦN 5 – Âm Lịch & Thông Báo

**Mục tiêu:** Hiển thị ngày âm lịch, alarm nhắc đúng giờ.

### Ngày 29–31: Âm Lịch
- [ ] Tích hợp `LunarCalendarUtil.java`
- [ ] Hiển thị ngày âm nhỏ dưới ngày dương trong Month View & Day View header
- [ ] Format: "15 tháng 4" hoặc "Rằm tháng 4 Giáp Thìn"

### Ngày 32–34: Thông Báo
- [ ] Tạo `NotificationHelper.java` với `NotificationChannel`
- [ ] Tạo `AlarmScheduler.java` dùng `AlarmManager.setExactAndAllowWhileIdle()`
- [ ] Tạo `ReminderReceiver.java` (BroadcastReceiver)
- [ ] Tạo `BootReceiver.java` → reschedule tất cả alarm sau reboot
- [ ] Test: tạo sự kiện 5 phút nữa, thông báo xuất hiện đúng giờ

**✅ Checkpoint tuần 5:** Âm lịch hiển thị đúng, thông báo hoạt động.

---

## TUẦN 6 – Chi Tiết Sự Kiện + Polish + Bug Fix

**Mục tiêu:** Màn hình chi tiết đầy đủ, app ổn định và đẹp.

### Ngày 35–37: Màn hình Chi Tiết Sự Kiện
- [ ] `EventDetailActivity` + MVP
- [ ] Hiển thị đầy đủ: tên, giờ, địa điểm, người chủ trì, ghi chú, thời gian thông báo
- [ ] Nút Sửa + Nút Xóa (dialog confirm → xóa DB + hủy alarm)

### Ngày 38–40: Sửa Sự Kiện
- [ ] `AddEventActivity` nhận `eventId` nếu là edit
- [ ] Presenter load event cũ → điền form
- [ ] Lưu → update DB + reschedule alarm

### Ngày 41–42: Polish UI & UX
- [ ] Empty state đẹp
- [ ] Loading indicator khi import file lớn
- [ ] Snackbar confirm sau khi lưu/xóa
- [ ] Icon và màu nhất quán
- [ ] Dark mode support (nếu có thời gian)

**✅ Checkpoint tuần 6:** App hoàn chỉnh, tất cả tính năng hoạt động ổn định.

---

## Bảng Tổng Hợp Tính Năng vs Tuần

| Tính năng | Tuần |
|---|---|
| MVP skeleton + Navigation | 1 |
| Room Database | 1 |
| Thêm sự kiện mới | 2 |
| Xem sự kiện theo ngày | 2 |
| Xem lịch tháng | 3 |
| Xem lịch năm | 3 |
| Nút về hôm nay | 3 |
| Import file .ics | 4 |
| Quản lý & filter nguồn lịch | 4 |
| Âm lịch | 5 |
| Thông báo + AlarmManager | 5 |
| Chi tiết & sửa sự kiện | 6 |
| Polish & Bug fix | 6 |

---

## Rủi ro & Phương án dự phòng

| Rủi ro | Xác suất | Phương án |
|---|---|---|
| iCal4j conflict với Android | Trung bình | Parse thủ công bằng BufferedReader |
| AlarmManager không hoạt động trên một số ROM | Cao | Dùng WorkManager thay thế |
| MaterialCalendarView không đủ linh hoạt | Thấp | Tự vẽ calendar bằng RecyclerView + GridLayoutManager |
| Bị kẹt ở MVP pattern quá lâu | Trung bình | Bỏ qua Contract ban đầu, refactor sau |

---

## Công cụ & môi trường

| Công cụ | Phiên bản gợi ý |
|---|---|
| Android Studio | Hedgehog 2023.1+ |
| JDK | 17 |
| Gradle | 8.x |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 (Android 14) |
| Device test | Emulator API 30+ hoặc thiết bị thật |

> 🏁 **Mục tiêu cuối:** Một ứng dụng lịch cá nhân hoàn chỉnh, code sạch theo MVP, có thể dùng thực tế hàng ngày — và là nền tảng để mở rộng thêm tính năng (sync Google Calendar, widget, v.v.).

</details>