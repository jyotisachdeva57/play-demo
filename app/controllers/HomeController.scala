package controllers

import javax.inject._

import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
  *
  *
  *
 */

case class Login(name:String,email:String)


@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport{
implicit val messages=cc.messagesApi

  val userForm = Form(

    mapping(
    "name" ->  text.verifying("name can not be empty",name=>name.nonEmpty),
    "email" -> email
  )(Login.apply)(Login.unapply))


  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */


  def index() = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.index(userForm))
  }


  def submit() = Action { implicit request  =>
    userForm.bindFromRequest.fold(
      errors=> BadRequest,
      user => {
        Redirect(routes.HomeController.redirect()).withSession("name"->user.name,"email"->user.email).flashing("value"->"successful")
      }
    )
  }

  def redirect()=Action {  implicit request  =>
   Ok(views.html.user())
  }

}

