package kz.post.jcourier.data.model.task

import androidx.annotation.Keep

@Keep
class TaskIdReason(
    val taskId: Long,
    val reason: String,
    val cancelReasonOther: String?
)

enum class CancelReasonDto {
    NA, //NOT_AVAILABLE  ** Нет в наличии
    IP, //INCORRECT_PRICE ** Некорректная цена
    DC, //UNABLE_TO_CONTACT_BUYER ** Не удалось связаться с покупателем
    ND, //NO_DELIVERY_TO_THE_REGION ** Нет доставки в регион
    O,  //OTHER (Другое. Для этого статуса дополнительно нужно заполнять поле "cancelReasonOther".)
    LOP,//LONG_ORDER_PROCESSING ** Долгая обработка заказа
    OC, //NOT_SATISFIED_WITH_THE_CHARACTERISTICS ** Не устроили характеристики
    LDT,//NOT_SATISFIED_WITH_THE_DELIVERY_TIME ** Не устроили сроки доставки
    PS, //GOODS_FROM_THE_SHOWCASE ** Товар с витрины
    AG, //DUPLICATE_ORDER_OR_ORDER_ALREADY_ISSUED ** Дубль заказа / Заказ уже выдан
    FC, //FOUND_CHEAPER ** Нашел(-а) дешевле
    C   //CHANGED_MY_MIND ** Передумал
}