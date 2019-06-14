package input;

import exceptions.CommunicationException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Communication {

    void shoot() throws CommunicationException;

    void giveUp() throws CommunicationException;

    void revoke()throws CommunicationException;

    void receive() throws CommunicationException;

    void ready() throws CommunicationException;

    void attack() throws CommunicationException;


}
