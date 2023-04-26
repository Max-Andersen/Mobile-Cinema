# Cinema lab

Один из учебных проектов второго курса HITs TSU.<br>
Мобильное приложение-кинотеатр с функционалом коллекций, подборок, обсуждением и просмотром фильмов(стоит точнить, что приложение не использовалось в коммерческих целях, авторские права не нарушены, для всех фильмов, кроме [Смешариков](https://www.smeshariki.ru/) были использованы трейлеры).<br>
Я работал с уже заготовленной [API](http://107684.web.hosting-russia.ru:8000/api/swagger/index.html?url=/api/static/cinema-api.yaml#/)
<details><summary>Описание API(когда-то её отключат и ссылка не будет загружаться)</summary>
Auth Регистрация и получение токена<br>

**POST** /auth​/register *Регистрация*<br>
**POST** /auth​/login *Аутентификация в системе*<br>
**POST** /auth​/refresh *Обновление access token'a с помощью refresh token'а*<br>


**Cover Обложка для приложения**<br>
**GET** /cover *Получить информацию о фильме для обложки.*<br>

**Movie Информация о киноновинках**<br>
**GET** /movies *Получить список фильмов.*<br>
**GET** /movies​/{movieId}​/episodes *Получить список эпизодов.*<br>
**POST** /movies​/{movieId}​/dislike *Убрать фильм из подборки.*<br>

**Collections Информация о коллекциях пользователя**<br>
**GET** /collections *Список коллекций данного пользователя.*<br>
**POST** /collections *Создать коллекцию.*<br>
**DELETE** /collections​/{collectionId} *Удаление коллекции*<br>
**GET** /collections​/{collectionId}​/movies *Получить список фильмов в коллекции*<br>
**POST** /collections​/{collectionId}​/movies *Добавить фильм в коллекцию*<br>
**DELETE** /collections​/{collectionId}​/movies *Удаление фильма из коллекции*<br>

**Tags Информация о тегах**<br>
**GET** /tags *Список всех тегов*<br>

**Episodes Информация о эпизодах**<br>
**GET** /episodes​/{episodeId}​/comments *Получить список комментариев к эпизоду.*<br>
**POST** /episodes​/{episodeId}​/comments *Добавить комментарий.*<br>
**GET** /episodes​/{episodeId}​/time *Получить текущую позицию.*<br>
**POST** ​/episodes​/{episodeId}​/time *Сохранить текущую позицию эпизода.*<br>

**Chats Информация о чатах**<br>
**GET** ​/chats *Список чатов, в которых участвует данный пользователь.*<br>
**GET** ​/chats​/{chatId} *Информация о чате.*<br>
**GET** ​/chats​/{chatId}​/messages *Список сообщений чата.*<br>
**POST** ​/chats​/{chatId}​/messages *Отправить сообщение.*<br>

**History Информация об истории**<br>
**GET** ​/history *История просмотров пользователя.*<br>

**Preferences Информация о предпочтениях**<br>
**GET** ​/preferences *Получить список предпочтений пользователя.*<br>
**PUT** ​/preferences *Изменить предпочтения пользователя.*<br>

**Profile Информация о профиле пользователя**<br>
**GET** ​/profile *Получить информацию о пользователе*<br>
**PATCH** ​/profile *Редактирование данных пользователя*<br>
**POST** ​/profile​/avatar *Загрузка фотографии*<br>

</details>

### **Используемые библиотеки**:
- [Retrofit](https://github.com/square/retrofit) - обёртка с кодогенерацией над OkHttp, упрощающая взаимодействие с [REST API](https://ru.wikipedia.org/wiki/REST)
- [OkHttp](https://square.github.io/okhttp/) - Использовал WebSocket для работы чатов
- [Lottie](https://github.com/airbnb/lottie-android/) - Анимация в ErrorDialog и Like/Dislike
- [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) - Ассинхронная работа с интернет-запросами.
- [Security crypto](https://developer.android.com/jetpack/androidx/releases/security) - для хранения токена пользователя в [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences)
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - Локальное сохранение и изменение картинки и названия коллекции. 
- [CardStackView](https://github.com/yuyakaido/CardStackView) - Слайдер, как в Tinder
- [Glide](https://github.com/bumptech/glide) - Загрузка фото через Интернет.

### **Особенности**:
- Live чаты на веб-сокетах
- Single Activity
- MVVM
- Clean Architecture(далеко не идеальная, но за неимением времени пока что такая, в будущем планирую доделать)
- Английская локализация


### **Визуальная составляющая**:


