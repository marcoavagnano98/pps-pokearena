package model.entities

trait Map:
  /**
   * @return entities return the list of entities on the screen
   *
   */
  def entities: List[VisibleEntity]

  /**
   * render the map on screen
   *
   */
  def render: Unit

  /**
   * update the map on screen
   *
   */
  def update: Unit

