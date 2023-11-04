package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidTicketException;
import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {


    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Ticket findTicketByTicketID(String ticketId) {
        return ticketRepository.findTicketByTicketId(ticketId);
    }

    @Override
    public List<Ticket> getTicketByDescriptionKeyword(String keyword) {
        return ticketRepository.findTicketByDescriptionContainingKeyword(keyword);
    }

    @Override
    public String createTicket(TicketDto ticketDto) {
        Date expDate = ticketDto.getExpDate();
        Date currentDate = new Date();

        // Check Date
        if (expDate != null && expDate.after(new Date(currentDate.getTime() + 24 * 60 * 60 * 1000))) {
            //Find Ticket
            if (ticketRepository.findById(ticketDto.getTicketId()).isPresent()) {
                return  "This Ticket ID has existed.";
            }

            // Create a new Ticket entity and populate it with data from the DTO
            Ticket newTicket = new Ticket();
            newTicket.setTicketId(ticketDto.getTicketId());
            newTicket.setTicketPrice(ticketDto.getTicketPrice());
            newTicket.setDescription(ticketDto.getDescription());
            newTicket.setVisitDate(ticketDto.getExpDate());

            // Save the new ticket to the database
            ticketRepository.save(newTicket);

            return "New ticket has been added successfully";
        } else {
            return "Please give me expDate greater than 1 day to get current date";
        }
    }



    @Override
    public String updateTicket(TicketDto ticketDto) {
        // Retrieve the existing Ticket entity by its ID
        Ticket existingTicket = ticketRepository.findById(ticketDto.getTicketId())
                .orElse(null);

        // Check if the ticket exists
        if (existingTicket != null) {
            Date expDate = ticketDto.getExpDate();
            Date currentDate = new Date();

            // Check Date
            if (expDate != null && expDate.after(new Date(currentDate.getTime() + 24 * 60 * 60 * 1000))) {
                // Update the ticket properties with data from the DTO
                existingTicket.setTicketPrice(ticketDto.getTicketPrice());
                existingTicket.setDescription(ticketDto.getDescription());
                existingTicket.setVisitDate(expDate);
                // Update other properties as needed

                // Save the updated ticket to the database
                ticketRepository.save(existingTicket);

                return "Ticket updated successfully";
            } else {
                return "Please give me expDate greater than 1 day to get current date";
            }
        } else {
            return "Ticket not found with ID: " + ticketDto.getTicketId();
        }
    }



    @Override
    public String removeTicket(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new InvalidTicketException("Not found this Ticket ID to delete."));

        ticketRepository.delete(ticket);

        return ticket.getTicketId();

    }

    @Override
    public List<Ticket> findAllTicket() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> findAllByTicketPriceAsc() {
        return ticketRepository.findAllByTicketPriceAsc();
    }

    @Override
    public List<Ticket> findAllByTicketPriceDesc() {
        return ticketRepository.findAllByTicketPriceDesc();
    }
}
