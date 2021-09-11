//Task
Java:
Есть сайт https://dadata.ru. Оттуда многие компании тянут данные по клиенту (все в рамках закона:slightly_smiling_face:)Там есть подсказки https://dadata.ru/suggestions/ - нам нужна подсказка по организации. У ресурса очень подробная документация, для нашей задачи читать тут: https://dadata.ru/api/suggest/party/
Задачи:
1. По части названия организации, например “Мос“, найти 20 активных юридических компаний.
2. Отсортировать по registration_date. (самая новая организация последняя в списке).
3. Вывести рандомную компанию (как результат работы метода). Название, дату регистрации ( в формате “DD-MM-YYYY”), инн.

Из обязательного - Java 8+
Из желательного - Java Stream Api

//Done
1. метод PrintAllSuggests класса DadataProvider
2. метод PrintSortedByRegistrationDateAsc класса DadataProvider
3. метод PrintRandomSuggest класса DadataProvider
