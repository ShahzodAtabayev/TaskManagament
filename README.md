# Projectc
Buni arxitectura componentalari (liveData, 
viewModel,navigation, retrofit, corountines...)
o'rganmagan paytimda qilganman.
TZ->
App ninig asosiy vazifasi qilinadigan ishlarni rejalashtirishga mo'ljallangan.
Dasturda quyidagi imkoniyatlar bo'lishi kerak:
- Asosiy oynasi(main);
- Yangi vazifa qo'shish oynasi(add task);
- Vazifalar safatchasi(basket);
- Barcha vazifalarni ko'rish;
- Vazifalarni tahrirlash;
- Vazifalar tarixi(history);
- Dastruni ulashish;
- Dasturdan foydanlanish shartlari;
- Dasturdan foydanalinsh yo'riqnomasi;

Dastur birinchi marta ishga tushganda Into oynasi unda dasturni mazmuni ochib berilishi kerak. Keyin foydanalish shartlari. 
Bular faqat birinchi marta ishga tushganidagina chiqishi kerak.

Vazifa quyidagilardan iborat bo'lishi shart:
- nom; 
- bajarilish sanasi
- hash taglar;
- muhimlilik darajasi;
- Vazifa matni;
Barcha oynadan vazifa tanlanganda uni to'liq ochish kerak bo'ladi. Ya'ni ko'p oynalarda vazifa to'liq ko'rsatilmaydi. Masalan matni juda uzun bo'lishi mumkin.
 Shu sababdan ko'p oynalarda qisqa ma'lumotlari ko'rsatiladi. 

Asosiy oynasi(main):
	Dasturning asosiy oynasida qilinishi kerak bo'lgan ishlar ro'yhati chiqadi. Ro'yhatda vazifalarda nomi, vaqti, sanasi, muhimlilik darajasi, 
hash tag(maxsus belgi). (+bunus uchun: qancha qolganini teskari timer bilan sanashi). Vazifalar quyidagi tartibda saralangan bo'ladi:
- Bugun qilinadigan ishlar. Bajarilish vaqti bugun bo'lgan ishlar birinchi ko'rsatiladi. Ular foydalanuvchiga biror belgi bilan ajratilgan holda 
ko'rsatilishi kerak. Masalan qizil rang bilan. Belgi shunday tanlanishi kerakki bugun bajarilmasa kech bo'lishini anglatish kerak.
- Bajarilishiga 1-3 kungacha vaqt bor vazifalar. Ular yaqinlashib qolganini bildiradigan belgi bilan ajratilishi kerak. 
- Bajarilishiga 3 kundan ko'p vaqt borlari esa odatiy tursin.
Diqqat! Vaqti tugagan yoki bajarilgan topshiriqlar bu ro'yhatda chiqmasin, ya'ni asosiy oynadagi ro'yhatda. Bundan tashqari vazifalarni bajarilgan yoki bekor
 qilingan ajratish kerak. Vaqti o'tib ketgan vazifa avtomatik bu ro'yhatdan olib tashlanadi, ya'ni ro'yhatda ko'rinmay qoladi. Yana bu oynadan qolgan oynalarga 
kirish imkonyati bo'lishi kerak.

Vazifalar safatchasi(hitory):
	Bu oynada o'chirilgan vazifalar ro'yhati turadi. Ularni qayta tiklash yoki butunlay o'chirib tashlash imkoniyati bo'ladi.
 Tiklangan vazifa vaqti bo'yicha o'z o'rniga tushishi kerak(vaqti o'tgan bo'lsa eski vazifalar qatoriga).

Barcha vazifalarni ko'rish:
	Bu oynada barcha vazifalarni ko'rish mumkin. Albatta savatchada bo'lmaganlardan boshqa. Ro'yxat quyidagi status bilan chiqarilishi kerak:
- Eskirgan (bajarilmasdan);
- Bajarilgan;
- Bekor qilingan;
- Vaqti kelmagan;
Bu oynadan turib yangi vazifani bekor yoki bajarilgan qilish mumkin. Eskilari esa o'zgartirib bo'lmaydi. 
(+bonus: Vazifani clone qilish mumkin, bunda yangi vaqt belgilanadi va yangi vazifa sifatida paydo bo'ladi. Qolgan paramtrlarini ham o'zgartirish mumkin)

Vazifalarni tahrirlash:
	Bu oyna orqali vazifalarni tahrirlash mumkin. Ularni o'zgartirish, o'chirish(o'chirilganlar savatga tushadi), bekor qilish mumkin.

Vazifalar tarixi(history):
	Bu oynada vazifalar uchta tabda chiqishi kerak, ya'ni bajarilgan, bekor qilingan va vaqi o'tib ketganlar. Ularni bu oynadan faqat ko'rish mumkin bo'lsin.

Dasturdan foydanlanish shartlari:
	Bu oynada foydalanish shartlari va talablari haqida ma'lumot bo'ladi. Dastur birinchi marta ishga tushganda kirishdan avval shu oyna ochilishi kerak. 
Keyinchalik esa asosiy oyna orqali kirib ko'rish mumkin bo'lsin.

Dasturdan foydanalinsh yo'riqnomasi:
	Bu oyna dasturdan foydanalish haqida qo'llanmadan iborat bo'lishi kerak. Oynalardan screen shotlar va har bir tugmani vazifasi haqida to'liq ma'lumotlardan
 iborat bo'lishi kerak.

Dastur design ishlab chiqish uchun yordam sifati rasmlari berilishi mumkin. Bu dastur shunday bo'lishi kerak degani emas. Bu sizga firk berish uchun yordam 
sifatida berilgan bo'ladi. Vazifa sharti mijoz nuqtaiy nazaridan yozildi. Qo'shimcha qo'shishingiz va kamchiliklarni to'g'rilashishingiz mumkin.
