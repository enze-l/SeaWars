package input;

import exceptions.CommunicationException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Communication {

    /**
     * declares that the enemy shoots on the target described by the next two following parameters
     * @throws CommunicationException if no proper coordinates come after shoot was initialized
     */
    void shoot() throws CommunicationException;

    /**
     * declares, that the ready status of the enemy should be revoked
     * @throws CommunicationException if no one is longer in the preparation-phase
     */
    void revoke()throws CommunicationException;

    /**
     * declares, that the player has to wait first for an attack
     * @throws CommunicationException if not both parties are in the game-status ready
     */
    void receive() throws CommunicationException;

    /**
     * declares, that the enemy has set all ships
     * @throws CommunicationException if the enemy wasn't in the preparation-phase before
     */
    void ready() throws CommunicationException;

    /**
     * declares, that the player should attack the enemy first
     * @throws CommunicationException if not both player were in the ready-phase before
     */
    void attack() throws CommunicationException;
}
