# Car Rental Service API

## Архитектура
При дадените изисквания архитектурата, която е създадене ше позволи използването на системата по указания начин
като оставя възможността за бъдещо разширение на функционалността при разразстване на опеацията на клиента.

## Защо има отделни записи за клиентите?
Таблицата и endpoint-ите свързани със записите за клиенти бяха добавени, въпреки че не бяха в началните изисквания,
защото дори ако тази система не изисква от потребителите да създават акаунти, за да я ползват, ще бъде полезно за клиента да има запис на данните за клиентите:
- Тези записи ще улеснят попълването на данни при повторно обаждане от клиента и ще ускори обслужването.

- Допълнително предимство на тази имплеметация е, че тя ще улесни преминаването на ситемата към пълно използване на акаунти,
управлявани от потребителите ако клиените пожелаят това в бъдеще (като при бъдещо разширение например).
- От страна на бизнеса ще е полезно да имат лесен достъп до начин да извилизат данни за демографията на потребителите, които да ползват при вземането на решения



