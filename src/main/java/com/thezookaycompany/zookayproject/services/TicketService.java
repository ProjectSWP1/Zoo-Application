package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.entity.Ticket;

public interface TicketService {
    Ticket findTicketByTicketID(String ticketId);

    Ticket createTicket(TicketDto ticketDto);

    String updateTicket (TicketDto ticketDto);

    String removeTicket(String id);

}
