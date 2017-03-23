package domain

case class Event(userId: String, itemId: String, event: String, timestamp: Long, price: Option[Double])
