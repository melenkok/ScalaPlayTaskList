package controllers

import com.example.playtutorial.shared.SharedMessages
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class SpaceShip @Inject() (cc: ControllerComponents) extends AbstractController(cc){

  def index = Action { implicit request =>
    Ok(views.html.spaceshipMain())
  }

  def create_user = Action {implicit  request =>
    Ok(views.html.spaceOpenPage())
  }

  def create(userId: String, fullName: String) = Action {
    Ok(s"$userId  is waiting for me")
  }

}
