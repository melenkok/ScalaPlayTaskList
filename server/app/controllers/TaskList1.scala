package controllers
import com.google.inject._
import models.TaskListInMemoryModel
import play.api.mvc._
import play.twirl.api._
import play.api.i18n._
import javax.inject.Singleton

import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String )



@Singleton
class TaskList1 @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc){
  val loginForm = Form(mapping(
    "Username" -> text(3,10) ,
    "Password" -> text(8)
  ) (LoginData.apply)(LoginData.unapply))


  def login = Action { implicit request =>
    Ok(views.html.login1(loginForm))
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in with $password")
  }

  def validateLoginPost = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map{ args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username/password combination")
    }.getOrElse( Redirect("login1"))
  }

  def createUserLoginForm = Action {implicit request =>
    loginForm.bindFromRequest.fold(
      formwithErrors => BadRequest(views.html.login1(formwithErrors)),
      ld => if (TaskListInMemoryModel.createUser(ld.username, ld.password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> ld.username)
      } else Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username/password combination")
    )
  }

  def createUser = Action { request =>
    val newUser = request.body.asFormUrlEncoded
    newUser.map { args =>
      val username = args("username").head
      val password = args("password").head
      if ( TaskListInMemoryModel.createUser(username, password) ) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "User already exists")
      }
    }.getOrElse( Redirect("login1"))
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.TaskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def logout = Action {
    Redirect(routes.TaskList1.login()).withNewSession
  }

  def addTask = Action {implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map {args =>
      val newTask = args("newTask").head
      TaskListInMemoryModel.addTask(username, newTask)
      Redirect(routes.TaskList1.taskList())
    }.getOrElse(Redirect(routes.TaskList1.taskList()))
  }.getOrElse(Redirect(routes.TaskList1.login()))
}

  def deleteTask = Action {implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map {args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

}
