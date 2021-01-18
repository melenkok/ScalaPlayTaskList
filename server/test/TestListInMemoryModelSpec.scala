import models.TaskListInMemoryModel
import org.scalatestplus.play._


class TestListInMemoryModelSpec extends PlaySpec {
  "TaskListInMemoryModel" must {
    "do valid login for defaul user" in{
      TaskListInMemoryModel.validateUser("Mark", "pass") mustBe(true)
    }
    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("Mark", "wrong") mustBe (false)
    }
    "reject login with wrong username" in {
      TaskListInMemoryModel.validateUser("ivan", "pass")
    }
    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("Mark") mustBe(List("eat", "code", "do videos"))
    }
    "create new user with no tasks" in {
      TaskListInMemoryModel.createUser("Mike", "password") mustBe(true)
      TaskListInMemoryModel.getTasks("Mike") mustBe(Nil)
    }
    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("Mark", "password") mustBe (false)
    }
    "add new task for default user" in {
      TaskListInMemoryModel.addTask("Mark","testing")
      TaskListInMemoryModel.getTasks("Mark") must contain ("testing")
    }
    "add new task for new user" in {
      TaskListInMemoryModel.addTask("Mike", "testing1")
      TaskListInMemoryModel.getTasks("Mike") must contain("testing1")
    }
    "remove task from default user" in{
      TaskListInMemoryModel.removeTask("Mark", TaskListInMemoryModel.getTasks("Mark").indexOf("testing")) mustBe(true)
      TaskListInMemoryModel.getTasks("Mark") must not contain ("testing")
    }
  }

}
