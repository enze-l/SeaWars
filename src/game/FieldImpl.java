package game;

/**
 * @author s0568823 - Leon Enzenberger
 */

class FieldImpl implements Field{
    private Coordinate coordinate;
    private FieldStatus fieldStatus;
    private Ship ship;

    /**
     * Each field of a board gets created as a part of the board. It knows its own position and can be assigned a ship
     * later on. As a default it's status gets assigned to "Water"
     * @param coordinate the position of the field on the board
     */
    FieldImpl(Coordinate coordinate){
        this.coordinate=coordinate;
        this.fieldStatus=FieldStatus.WATER;
    }

    /**
     * Method to assign a ship to the field
     * @param ship the type of ship that gets positioned (partly) on the field
     */
    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Method to attack the field. If it is assigned a ship, it calls the method of the ship to receive the shot.
     * For debugging-purposes, the status gets saved in the field too.
     * Else, it changes the FieldStatus to "SHOTWATER". This Status doesn't get changed if the Field gets attacked again.
     */
    @Override
    public  void receiveHit() {
        if(this.ship!=null){
            this.ship.setHit(coordinate);
            this.fieldStatus=this.ship.getShipStatus(coordinate);
        }
        else this.fieldStatus=FieldStatus.SHOTWATER;
    }

    /**
     * Method for getting the FieldStatus. In the case there is a ship placed on the field, the status of the ship gets
     * accessed instead of the field. This is needed because the status of the field might be outdated if the ship has
     * been sunk.
     * @return status of the field
     */
    @Override
    public FieldStatus getStatus() {
        if(this.ship!=null){
            return this.ship.getShipStatus(coordinate);
        }
         else return this.fieldStatus;
    }
}
