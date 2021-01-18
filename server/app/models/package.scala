package models
import collection.mutable

object TaskListInMemoryModel {

  private val users = mutable.Map[String, String]("Mark" -> "pass")
  private val tasks = mutable.Map[String, List[String]] ("Mark" -> List("eat", "code", "do videos"))

  //check if username-password is correct
  def validateUser(username: String, password:String) :Boolean = {
    users.get(username).map(_ == password ).getOrElse(false)
    //get username if there is not one return (getOrElse) -> false
    //if there is one check if the password associated to that user equals password given to validate
  }

  //Boolean because it will check if the username already exists and if it does it will return False
  //if not it will create that user and return True
  def createUser(username: String, password: String): Boolean = {
    if (users.contains("username")) {
      false
    }
    else {
      users(username) = password
      true
    }
  }

  //based on the username get its tasks
  def getTasks(username: String) : Seq[String] = {
    tasks.get(username).getOrElse(Nil)
  }

  // Unit because this always works we can always add a task
  def addTask(username: String, task: String) : Unit = {
    tasks(username) = task :: tasks.get(username).getOrElse(Nil)
  }

  //Remove a task from list of tasks
  def removeTask(username: String, index: Int) : Boolean = {
    if (index<0 || tasks.get(username).isEmpty || index>= tasks(username).length) false
    else {
      tasks(username) = tasks(username).patch(index, Nil, 1)
        true
    }
  }





}
