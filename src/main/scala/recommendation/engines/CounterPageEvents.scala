package recommendation.engines

import cassandra._
import config.Config
import domain.Event

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class CounterPageEvents(storage: CassandraStorage) {

  def trackEvent(event: Event): Unit = {

    val userId = event.userId
    val itemId = event.itemId
    val action = event.event
    val weight = Config.ActionWeights(action)
    val timestamp = event.timestamp

    if (action == "click") checkUserViews(userId, ViewItem(itemId, timestamp))

  }

  def checkUserViews(userId: String, item: ViewItem): Unit = {
    val userViews = storage.userViews.getById(userId)
    userViews.onComplete {
      case Failure(msg) => println(msg)
      case Success(None) => saveNewUserViews(userId, item)
      case Success(Some(views)) => updateUserViews(views, item)
    }
  }

  def saveNewUserViews(userId: String, item: ViewItem): Unit = {
    storage.userViews.store(UserViews(userId, item :: Nil))
  }

  def updateUserViews(lastViews: UserViews, newItem: ViewItem): Unit = {
    if (lastViews.views.exists(_.itemId == newItem.itemId)) {
      val updatedViews = lastViews.views.filter(_.itemId != newItem.itemId)
      storage.userViews.store(UserViews(lastViews.userId, newItem :: updatedViews))
    }
    else {
      storage.userViews.store(UserViews(lastViews.userId, (newItem :: lastViews.views).take(Config.RecentViewsListSize)))
    }
  }

  def getRecentViews(userId: String): Future[List[ViewItem]] = {
    storage.userViews.getById(userId).map {
      case Some(views) => views.views
      case None => Nil
    }
  }

}
