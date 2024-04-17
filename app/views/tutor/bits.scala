package views.html.tutor

import lila.app.templating.Environment.{ *, given }
import lila.ui.ScalatagsTemplate.{ *, given }
import lila.tutor.TutorNumber

object bits:

  val mascot =
    img(
      cls := "mascot",
      src := assetUrl("images/mascot/octopus-shadow.svg")
    )

  def mascotSays(content: Modifier*) = div(cls := "mascot-says")(
    div(cls := "mascot-says__content")(content),
    mascot
  )

  val seeMore = a(cls := "tutor-card__more")("Click to see more...")

  def percentNumber[A](v: A)(using number: TutorNumber[A]) = f"${number.double(v)}%1.1f"
  def percentFrag[A](v: A)(using TutorNumber[A])           = frag(strong(percentNumber(v)), "%")

  private[tutor] def otherUser(user: User)(using ctx: Context) =
    ctx.isnt(user).option(userSpan(user, withOnline = false))

  private[tutor] def layout(
      menu: Frag,
      title: String = "Lichess Tutor",
      pageSmall: Boolean = false
  )(content: Modifier*)(using PageContext) =
    views.html.base.layout(
      moreCss = cssTag("tutor"),
      modules = jsModule("tutor"),
      title = title,
      csp = defaultCsp.withInlineIconFont.some
    ):
      main(cls := List("page-menu tutor" -> true, "page-small" -> pageSmall))(
        views.html.base.bits.subnav(menu),
        div(cls := "page-menu__content")(content)
      )
