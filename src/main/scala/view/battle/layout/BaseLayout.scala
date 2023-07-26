package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Actor}
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.World.Position

enum LayoutVisibility(val value: Boolean):
  case Visible extends LayoutVisibility(true)
  case NotVisible extends LayoutVisibility(false)

abstract class BaseLayout(boundary: Rectangle) extends Table :
  type T
  def updateLayout(newLayoutInfo: T): Unit
  def updateActorByIndex(actor: Actor, i: Int): Unit = getCells.items(i).setActor(actor)
  
  setBounds(boundary.x, boundary.y, boundary.width, boundary.height)