# hotel-management (Source Code Phần Mềm Quản Lý Khách Sạn Viết Bằng Java Framework Spring MVC)
Simple hotel management desktop software build base on Java with Spring Data, Maven, Jasper Report and Java Fx8.
<br/>
<br/>
Phần mềm này được viết nhìn chung khá ổn, theo mô hình MVC, tổ chức theo mô hình 3 lớp. Đặc biệt là sử dụng Spring Boot và Spring Data để tương tác cơ sở dữ liệu, khác là đặc biệt khi đây là một phần mềm đúng không nào. 
Bên cạnh đó sản phẩm này sử dụng Java Fx 8 để làm giao diện, một công nghệ làm GUI mới của Java rất được ưa chuộng và dễ dàng thao tác.

<img src="https://3.bp.blogspot.com/-eUZMgA1Hzss/W6Wej33Ce0I/AAAAAAAAQLM/wBd6X0N8VkQsQB2y2YxJZ13dLbQeWPA1wCLcBGAs/s1600/hm1.jpg"/>

## PHẦN 1. GIỚI THIỆU  
###  1.1. Giới thiệu khái quát
Tên dự án : Phần mềm quản lý khách sạn (Hotel Management ).
<br/>
Mục tiêu : Xây dựng phần mềm hỗ trợ công tác quản lý khách sạn. Giúp thao tác quản lý phòng , đặt phòng dễ dàng hiệu quả.
<br/>
a) Yêu cầu cơ bản 
+ Hệ thống có thể quản lý được việc đặt phòng và thanh toán phòng.
+ Giao diện thân thiện dễ sử dụng.
+ Có thể trích xuất báo cáo, hóa đơn thanh toán.
+ Có thể tìm kiếm , tra cứu thông tin về tình trạng phòng , khách hàng.

b) Phạm vi dự án hiện tại
<br/>Hiện tại dự án đã được triển khai dưới dạng phần mềm cài đặt theo gói, đối tượng hướng đến chính là đội ngũ nhân viên quản lý của khách sạn, đã đáp ứng được các nhu cầu cơ bản được yêu cầu
Hệ thống chạy ổn định trên nền tảng hệ điều hành Windows 7 trở lên và Linux.

### 1.2. Giải pháp và công nghệ sử dụng
Hệ thống được xây dựng dựa trên mô hình MVC. Sử dụng ngôn ngữ lập trình Java. Công nghệ sử dụng để xây dựng giao diện ứng dụng là Java FX 8 kết hợp với Framework Spring MVC và công nghệ Spring Data JPA để tương tác cơ sở dữ liệu.

<br/>
Ứng dụng sử dụng các thành phần cho mô hình MVC như sau.
<br/>

+ View : Sử dụng Java FX 8.
+ Model : Sử dụng công nghệ Spring Data JPA kết hợp với Java Persistence API và Hibernate Bean Validation để ràng buộc và xây dựng các bảng cơ sở dữ liệu.
+ Controller: Sử dụng kết hợp giữa tính năng Controller của Java FX 8 và Dependency Injection contaier của Spring .


<br/>Cơ sở dữ liệu: Hệ thống sử dụng cơ sở dữ liệu MySQL.



<br/>Tiện ích bên thứ ba: Hệ thống sử dụng Jasper Report API để xây dựng nền tảng trích xuất báo cáo.

## PHẦN 2. TÍNH NĂNG
 Sản phẩm bao gồm các tính năng chính sau đây: 

### 2.1. Quản lý đặt phòng
  
 - Xem thông tin phiếu đặt phòng.
 - Thay đổi thông tin phiếu đặt phòng.
 - Tìm kiếm phòng.
 - Thanh toán phòng.
 - Xuất hóa đơn thanh toán

### 2.2 Quản lý báo cáo
  
  a) Xuất báo cáo hằng ngày.<br/>
  b) Xuất báo cáo theo mốc thời gian.<br/>
  c) Xuất báo cáo tổng doanh thu.<br/>

### 2.3 Quản lý phần mềm
  
  a) Cài đặt đa ngôn ngữ<br/>
  b) Tùy chỉnh thông tin cơ bản của khách sạn.<br/>
## PHẦN 3. ĐẶC TẢ YÊU CẦU 

###  3.1. Xem thông tin phiếu đặt phòng

#### 3.1.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng xem thông tin của phiếu đặt phòng , mức độ quan trọng cao

#### 3.1.2 Yêu cầu cho tính năng
  - REQ-1: Hiện thị rõ ràng dễ đọc.
  - REQ-2: Yêu cầu đủ các thông tin quan trọng như tên khách hàng , giờ vào , giờ ra , địa chỉ , ghi chú , số điện thoại.
  - REQ-3: Có thể xem được thông tin về phòng ( như đơn giá theo giờ , sức chứa , loại phòng , v.v ).

### 3.2. Xem thông tin phiếu đặt phòng

#### 3.2.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng cập nhật chỉnh sửa thông tin của phiếu đặt phòng , mức độ quan trọng cao

#### 3.2.2 Yêu cầu cho tính năng
  - REQ-1: Biểu mẫu nhập dễ dàng , dễ hiểu.
  - REQ-2: Có thông báo lỗi khi nhập sót các trường quan trọng như (Số CMND , số điện thoại , v.v).
  - REQ-3: Khi đã xác nhận chọn phòng (Tức đã xác định là thuê hoặc đặt trước trong một khoảng thời gian thì không được thay đổi, trừ phi xóa phiếu và lập lại).
  - REQ-4: Có thể xem được toàn bộ khách hàng của phòng hiện tại đang chọn (Nếu đã chọn phòng).
  - REQ-5: Có tính năng hỗ trợ chọn phòng , nếu khách hàng đó chỉ đang đăng
ký mốc thời gian và chưa chọn phòng cụ thể ( Tính năng Đặt phòng trước ).

### 3.3 Tạo phiếu đặt phòng

#### 3.3.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng tạo mới phiếu đặt phòng, mức độ quan trọng cao

#### 3.3.2 Yêu cầu cho tính năng
  - REQ-1: Biểu mẫu nhập dễ dàng , dễ hiểu.
  - REQ-2: Có thông báo lỗi khi nhập sót các trường quan trọng như (Số CMND , số điện thoại , v.v).
  - REQ-3: Khi đã xác nhận chọn phòng (Tức đã xác định là thuê hoặc đặt trước trong một khoảng thời gian thì không được thay đổi, trừ phi xóa phiếu và lập lại).
  - REQ-4: Có thể xem được toàn bộ khách hàng của phòng hiện tại đang chọn (Nếu đã chọn phòng)
  - REQ-5: Có tính năng hỗ trợ chọn phòng , nếu khách hàng đó chỉ đang đăng ký mốc thời gian và chưa chọn phòng cụ thể ( Tính năng Đặt phòng trước ).

### 3.4. Thanh toán phòng

#### 3.4.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng thanh toán phòng , mức độ quan trọng cao

#### 3.4.2 Yêu cầu cho tính năng
  - REQ-1: Chỉ được phép thanh toán phòng khi khách đã nhận phòng.
  - REQ-2: Có cảnh báo xác nhận việc thanh toán phòng.
  - REQ-3: Có hỗ trợ việc nhập số tiền khách đưa để tính toán tiền thối lại.
  - REQ-4: Sau khi thanh toán phải chuyển trạng thái đơn hàng để dễ nhận biết
  - REQ-5: Khi thanh toán xong nếu khách hàng chọn in hóa đơn , thì phải hiển thị màn hình in hóa đơn.

### 3.5. Tìm kiếm

#### 3.5.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng tìm kiếm các phiếu đặt phòng với các điều kiện ràng buộc và từ khóa , mức độ quan trọng cao

#### 3.5.2 Yêu cầu cho tính năng
  - REQ-1: Có ô nhập từ khóa tìm kiếm.
  - REQ-2: Có thể chọn tìm kiếm theo Tên , số CMND hoặc theo phòng.
  - REQ-3: Có thể lọc hoặc tìm kết quả theo khoảng ngày.
  - REQ-4: Hiển thị dữ liệu theo dạng bảng, và có thể sắp xếp kết quả tăng hoặc giảm dần.
  - REQ-5: Có tính năng lọc kết quả tìm kiếm theo ( Loại phòng , tình trạng phòng , … ).

### 3.6 Quản lí báo cáo

#### 3.6.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng quản lí việc trích xuất báo cáo theo các hóa đơn , mức độ quan trọng cao

#### 3.6.2 Tài liệu đính kèm
Mẫu thiết kế được đính kèm trong tài liệu.

#### 3.6.3 Yêu cầu cho tính năng
  - REQ-1: Trích xuất báo cáo dễ dàng.
  - REQ-2: Hỗ trợ tối thiểu hai định dạng là Docx và Excel .
  - REQ-3: Có hỗ trợ báo cáo theo ngày , theo khoảng thời gian .

### 3.7 Tùy chỉnh phần mềm

#### 3.7.1 Mô tả và độ quan trọng
Tính năng cho phép người dùng cài đặt một số tính năng tương tác với phần mềm như ngôn ngữ , tên khách sạn , địa chỉ . Mức độ quan trọng cao

#### 3.7.2 Yêu cầu cho tính năng
  - REQ-1: Có thể cài đặt đa ngôn ngữ ( Tiếng việt và tiếng anh).
  - REQ-2: Có thể cập nhật thông tin cơ bản của khách sạn như tên , địa chỉ , số thuế, số điện thoại , v,v .

## PHẦN 4. Hướng Dẫn Sử Dụng
 Tài liệu hướng dẫn sử dụng , sử dụng những hình ảnh trực quan nhất của ứng dụng, là sản phẩm khi đã hoàn thiện ( Khác với thiết kế sơ bộ ban đầu ).

### 4.1 Tổng Quát Các Tính Năng Ở Màn Hình Chính
 Sau khi đăng nhập vào ứng dụng ta có được màn hình quản lý chính như dưới đây.
 <img src="https://3.bp.blogspot.com/-5A-6u8BZL6w/W6Wi0ktvLtI/AAAAAAAAQLY/5brxqY7nqIMbEH56FEDa_70feQOVZI-1wCLcBGAs/s1600/hm2.jpg"/>
 
 <b style="text-align:center">(H-8 . Giao diện màn hình quản lý chính)</b>

Thanh công cụ màu xanh lá cây trên cùng có hai chức năng :

  + Đặt Phòng : Cho phép ta tạo phiếu đặt phòng mới.

  + Tùy Chỉnh : Cho phép ta cài đặt các thông số cho ứng dụng như tên khách sạn, ngôn ngữ hiển thị, mã số thuế , số điện thoại liên lạc , v.v

Thanh màu cam bên tay phải là thanh điều hướng , để chuyển đổi giữa các màn hình với nhau. Có hai chức năng chính là:

  1) Quản lý : Mở giao diện quản lý chính (Như hình trên)
<br/>
  2) Mở Của Sổ Quản Lí Báo Cáo : Mở cửa sổ quản lí việc xuất báo cáo.

vùng chính giữa là vùng tương tác, ta có 4 phần quan trọng sau đây.
Vùng Thông Tin Phiếu Đặt Phòng : Chứa thông tin về khách hàng và thời gian sử dụng phòng.

Vùng bảng dữ liệu tình trạng đặt phòng : Chứa tất cả các phiếu đặt phòng hiện tại, có tính năng cho phép tìm kiếm , lọc kết quả ,sắp xếp ( Nhấn chuột vào mỗi hàng sẽ tự động cập nhật toàn bộ thông tin của phiếu ấy lên màn hình).

- Vùng thanh toán: Chứa tính năng thanh toán phòng hiện tại và in hóa đơn, có hỗ trợ tình toán tiền thối lại cho khách.

- Vùng thông tin phòng: Chứa thông tin của phòng hiện tại như sức chứa , giá tiền , loại phòng , diện tích , v.v.

### 4.2 Hướng Dẫn Tạo Mới Phiếu Đặt Phòng

Từ màn hình quản lí chính , ta nhấp chuột nút “Đặt Phòng” ở thanh công cụ màu xanh lá cây ở góc trái trên cùng , của sổ tạo mới phiếu đặt phòng sẽ hiển thị như dưới đây.
 
 <img src="https://3.bp.blogspot.com/-lW947YFC_1Q/W6WjQR4Ej1I/AAAAAAAAQLg/2J6u0yn4JQEodz_oJgUOimE4VpTS8nPPACLcBGAs/s1600/hm3.jpg"/>
 
 Sau khi đã nhập đầy đủ các thông tin như ngày giờ và thời gian, chúng ta có thể nhấn vào nút “Lưu” màu xanh dương ở dưới để lưu tạm phiếu đặt phòng. Trong trường hợp ta muốn chọn phòng cho khách hàng ,thì tại phần “Phòng” ta nhấn vào nút “Tìm kiếm” cửa sổ tìm kiếm phòng sẽ hiện ra. Tại cửa sổ tìm kiếm phòng này , hệ thống sẽ tự động tìm ra phòng phù hợp với mốc
 thời gian vào ra mà ta đã nhập từ trước hoặc có thể nhập lại nếu cần thiết.
 <br/>
 Lúc này ta có thể nhấn chọn từng dòng để xem thông tin về phòng . Ở đây hệ thống sẽ tự động sắp xếp sao cho phòng trống gần nhất với hiện tại sẽ ở trên cùng kèm với thông tin về thời gian mà khách phải đợi cho việc nhận phòng.
 
 <br/>Ví dụ : Room A123 Có khoảng cách 6 ngày 2 giờ có nghĩa là nếu ta chọn phòng này thì từ thời điểm hiện tại đến đó còn cách 6 ngày . Tính năng này hỗ trợ tốt cho việc đặt trước phòng.

### 4.3 Hướng Dẫn Cập Nhật Thông Tin Phiếu Đặt Phòng
<br/> Từ màn hình chính sau khi đã chọn từ bảng dữ liệu phiếu đặt phòng muốn chỉnh sửa, ta nhấn vào nút “Sửa” ở góc phải trên cùng trong phần “Thông Tin Khách Hàng” của sổ chỉnh sửa sẽ hiện lên, sau khi chỉnh sửa sau ta nhấn vào nút “Lưu” để hoàn tất và lưu thông tin.
<br/> Nếu bạn nhập thiếu các trường quan trọng bắt buộc như là số CMND , Tên , Số điện thoại thì hệ thống sẽ báo đỏ các trường đó và hiển thị thông báo bắt buộc nhập đủ.
 
### 4.4 Hướng Dẫn Tìm Kiếm Thông Tin
<br/>Tại màn hình chính , ta thấy phần tìm kiếm nằm ở vùng “Bảng dữ liệu” . Tại đây ta có thể nhập từ khóa để tìm kiếm . Chú ý mặc định khi bạn nhập từ khóa sẽ tìm theo tên khách hàng, nếu bạn muốn thay đổi có thể đổi tìm kiếm theo tên phòng hoặc theo số CMND. Cụ thể ở hình dưới.
 
 <img src="https://4.bp.blogspot.com/-mcHnftiZE2g/W6WjhsgCqtI/AAAAAAAAQLo/zIFJvzxnUS4jnxoTNlEd1pxDqBSidAIkACLcBGAs/s1600/hm4.jpg"/>
 
 <b style="text-align:center">( H-10. Sử dụng tính năng tìm kiếm )</b>

<br/>Hệ thống có hỗ trợ tìm kiếm theo khoảng ngày , giúp bạn có thể dễ dàng thống kê các đơn hàng trong khoảng thời gian nhất định.
 
 ### 4.5 Hướng Dẫn Thay Đổi Cài Đặt Phần Mềm
 màn hình chính , ta nhấn vào nút “Tùy Chỉnh” ở thanh công cụ trên cùng . Cửa sổ tùy chỉnh sẽ hiển thị như dưới đây.
 <img src="https://1.bp.blogspot.com/-lsMd13m10Kw/W6WkpD72qvI/AAAAAAAAQL0/9mYsXG1R08sRchDS4gZcR_U2pyeh9YK5QCLcBGAs/s1600/hm5.jpg"/>
 <b style="text-align:center">( H-11. Tùy chỉnh phần mềm )</b>

<br/>Đây bạn có thể nhập lại các thông tin cần thiết khác cho khách sạn, đặt biệt bạn có thể thay đổi ngôn ngữ hiển thị của ứng dụng nếu cần thiết, để tiện cho việc sử dụng.
 <img src="https://1.bp.blogspot.com/-eQ-nNBKOu48/W6Wk6mKZt3I/AAAAAAAAQL8/FvJBXGzhKp4T-X6IFUHbJKerzVjakACzACLcBGAs/s1600/hm6.jpg"/>
 <b style="text-align:center">( H-12. Giao diện hiển thị bằng tiếng anh )</b>

### 4.6  Hướng Dẫn Xuất Báo Cáo
 Tại màn hình quản lý chính , ta nhấn vào nút “Báo cáo” ở thanh bên tay trái, cửa sổ quản lý báo cáo sẽ mở ra.
 
 <img src="https://4.bp.blogspot.com/-OKfIq5lcPv8/W6WlTCpLQLI/AAAAAAAAQME/r6iAOXVLtUYjITFJdLSzZmVM7RkhrposQCLcBGAs/s1600/hm7.jpg"/>
 <b style="text-align:center">(H-13. Cửa sổ quản lí báo cáo)</b>

<br/>Tại đây bạn có thể nhấn chọn vào loại báo cáo bạn muốn trích xuất, một hộp thoại yêu câu nhập mốc thời gian sẽ hiển thị ra (Nếu có). Sau khi nhập đầy đủ các thông tin cần thiết ,
 nhấn “Hoàn tất” và chờ một lát, cửa sổ in báo cáo sẽ xuất hiện (Như hình bên dưới).
 <img src="https://3.bp.blogspot.com/-7JydyUYyxHo/W6WlnaNpeuI/AAAAAAAAQMM/ilXjr-0Fx8MygkLMMI9Q3W_24M0StUv8gCLcBGAs/s1600/hm8.jpg"/>
 <b style="text-align:center">(H-14. Mẫu báo cáo doanh thu)</b>
<br/>Tại cửa sổ in ấn , bạn có thể nhấn vào biểu tượng máy in ở góc trái trên cùng để in báo cáo. Hoặc nhấn chọn vào nút “Save” để lưu báo cáo, tại đây bạn có thể chọn định dạng muốn lưu ( Excel hoặc Docx ) tùy theo mục đích của bạn.
 
### 4.7 – Hướng Dẫn Thanh Toán Phòng
 Để thanh toán phòng đầu tiên bạn phải nhận phòng cho khách trước, để làm điều này bạn nhấn chọn vào phiếu đặt phòng tương ứng ở bảng dữ liệu, lúc này trên vùng “Thông tin khách hàng” bạn sẽ thấy một nút nhấn “Nhận phòng” màu vàng cam có hình chìa khóa. (Hình dưới)
 <br/>Nhấn vào nút đó để nhận phòng , vào lúc nào bạn có thể thực hiện thủ tục thanh toán.
 <img src="https://4.bp.blogspot.com/-8pv6b7GeBhk/W6Wl09_HROI/AAAAAAAAQMQ/PhaCZtFWVWYHdZI7rXiNSJjzs4OsP6q5ACLcBGAs/s1600/hm9.jpg"/>
 <b style="text-align:center">( H-15. Nút nhận phòng ở vùng thông tin khách hàng )</b>
<br/>Sau khi nhấn vào nhận phòng , thì lúc này nút “Thanh Toán” màu xanh dương ở góc trên cùng bên phải sẽ hiển thị và cho phép sử dụng. Nhấn vào đó để thanh toán phòng , sẽ có hộp thoại yêu cầu xác nhận hành động này.
<br/>Chú ý rằng nếu bạn muốn in hóa đơn thì chỉ cần tích chọn ô “In hóa đơn” hoặc không bạn có thể bỏ chọn. Sau khi nhấn vào thanh toán, hệ thống sẽ tự động hiển thị màn hình in hóa đơn. (Hình dưới )
<img src="https://3.bp.blogspot.com/-hQFzNg2p3v8/W6WmBkBcbuI/AAAAAAAAQMY/C4HGQ7N3bnEvJPQL-FB6Ossjf-eI8V2igCLcBGAs/s1600/hm10.jpg"/>
<b style="text-align:center">( H-16. Màn hình in hóa đơn sau khi thanh toán phòng thành công )</b>

## 6. Mã Nguồn và hướng dẫn cài đặt

<br/>Hướng dẫn cài đặt và chạy:
<br/> Vì chương trình được đóng gói bằng Maven và chia nhỏ thành nhiều Modules nên các bạn cần cài đặt Maven lên IDE mà các bạn dùng để chạy nhé. Trước đây thì mình sử dụng Esclipse.
Sau đó các bạn phải chạy lệnh maven install để maven download và cài đặt đầy đủ các thư viện mà phần mềm cần. (P.s nếu dùng IDE thì có thể nó sẽ auto chạy giúp, hoặc các bạn tìm nút 'maven build')
Một điểm lưu ý khác nữa là phần mềm này sử dụng Spring Boot kết hợp với Spring Data, chính vì vậy có thể các bạn sẽ phải tìm hiểu một chút về Spring Data để hiểu được cách mình viết. 
<br/> Mục đích của các Module mình chia nhỏ ra trong phần mềm này:

1. hotel-management-persistence: Module này sử dụng Spring data để kết nối đến cơ sở dữ liệu, thực hiện các tác vụ Create, Update, View , Delete (CRUD) cho ứng dụng này.
2. hotel-management-business: Chứa các phương thức nghiệp vụ của phần mềm, như tính toán giá phòng, ... 
3. hotel-management-service: Đây là module chứa tất cả các dịch vụ (tính năng) mà ứng dụng cần có (ví dụ Search phòng, Xếp phòng, ...). 
Mình chia ra services và business riêng ra là bởi vì thế này, các bạn hình dung ví dụ như mình nói Service search phòng trống để xếp khách vào thì bên dưới nó sẽ thực thi hai nghiệp vụ là tìm tất cả phòng + nghiệp vụ xác định phòng đó là trống (kiểm tra chưa có ai đặt, phòng không bị hư, ...). Nghĩa là theo thiết kế của mình một Service bao gồm một tập nghiệp vụ Business bên trong. Việc chia nhỏ các business (logic nghiệp vụ) của phần mềm ra là hợp lý, vì nghiệp vụ rất dễ thay đổi và bản thân mỗi nghiệp vụ thường khá riêng biệt (theo đúng thực tế cuộc sống). 
4. hotel-management-desktop: Module này sử dụng JavaFX 8 để xây dựng lên giao diện người dùng cho phần mềm, và nó sử dụng module services là chính. Trong ngày mình có gọi thêm một số thư viện của Jasper Report để trích xuất báo cáo, Mình thì mình sử dụng template jasper riêng sau đó sử dụng  module service để đổ dữ liệu vào sinh ra báo cáo (cách làm này hiệu quả hơn, vì báo các có thể thiết kế riêng hoàn toàn mà không cần phụ thuộc vào phần mềm).
Trong lần đầu chạy, các bạn phải đổi thông tin kết nối database ở file theo link dưới đây:

```code
hotel-management/hotel-management-parent/hotel-management-persistence/src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost/hotel_management_db
spring.datasource.username=root
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.driverClassName=com.mysql.jdbc.Driver

spring.jpa.show-sql=true

spring.jpa.hibernate.ddl-auto = update
spring.jpa.database-platform = org.hibernate.dialect.MySQL5Dialect
```

 Đổi lại đường dẫn kết nối, username và password của MySQL database. 
 
