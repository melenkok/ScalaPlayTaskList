package controllers

import models.TaskListInMemoryModel
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject

class TaskList3 @Inject() (cc: ControllerComponents) extends AbstractController(cc){

  case class UserData(username: String, password: String)
  implicit val userDataReads = Json.reads[UserData]

  def load = Action { implicit request =>
    Ok(views.html.version3Main())
  }

  def data = Action {
    Ok(Json.toJson("a", "b", "c"))
  }

  def validate = Action { implicit request =>
    request.body.asJson.map { body => Json.fromJson[UserData](body) match{
      case JsSuccess(ud, path) =>
        if (TaskListInMemoryModel.validateUser(ud.username , ud.password)) {
          Ok(Json.toJson(true))
            .withSession("username" -> ud.username, "csrdToken" -> play.filters.csrf.CSRF.getToken.get.value)
        } else {
          Ok(views.html.login2())
        }
      case e @ JsError(_) => Redirect(routes.TaskList3.load())
    }
    }.getOrElse(Redirect(routes.TaskList3.load()))
  }

}
