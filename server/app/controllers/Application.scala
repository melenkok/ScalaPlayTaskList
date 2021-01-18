package controllers

import com.example.playtutorial.shared.SharedMessages
import play.api.mvc._

import javax.inject._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
      Ok(views.html.index(SharedMessages.itWorks))
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }

  def randomString (length: Int) = Action {
    Ok(util.Random.nextString(length))
  }
}

