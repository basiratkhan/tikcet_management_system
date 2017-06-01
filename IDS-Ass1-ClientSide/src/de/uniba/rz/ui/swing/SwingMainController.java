package de.uniba.rz.ui.swing;

import de.uniba.rz.app.Shutdown;
import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.TicketException;
import de.uniba.rz.entities.Type;

public class SwingMainController {

	private SwingMainModel model;
	private MainFrame mainFrame;
	private Shutdown connector;

	public SwingMainController(Shutdown shutdownConnector) {
		this.connector = shutdownConnector;
	}

	public SwingMainController(SwingMainModel model, MainFrame mf) {
		this.model = model;
		this.mainFrame = mf;
	}

	public void setSwingMainModel(SwingMainModel model) {
		this.model = model;
	}

	public void setMainFrame(MainFrame mf) {
		this.mainFrame = mf;
	}

	public void start() {
		if (mainFrame != null && model != null) {
			mainFrame.showUI();
		}
	}

	public void getAndShowTicketById(int id) {
		try {
			mainFrame.showTicketDetails(model.getTicket(id));
		} catch (TicketException e) {
			mainFrame.clearTicketDetails();
			mainFrame.showErrorDialog("Ticket with Id " + id + "does not exist!", e);

		}
	}

	public void acceptTicket(int id) {
		try {
			model.acceptTicket(id);
			mainFrame.showTicketDetails(model.getTicket(id));
		} catch (TicketException exec) {
			mainFrame.showErrorDialog("Invalid status change.", exec);
		}
	}

	public void closeTicket(int id) {
		try {
			model.closeTicket(id);
			mainFrame.showTicketDetails(model.getTicket(id));
		} catch (TicketException e) {
			mainFrame.showErrorDialog("Invalid status change.", e);
		}
	}

	public void rejectTicket(int id) {
		try {
			model.rejectTicket(id);
			mainFrame.showTicketDetails(model.getTicket(id));
		} catch (TicketException e) {
			mainFrame.showErrorDialog("Invalid status change", e);
		}
	}

	public void createNewTicket(String reporter, String topic, String description, Type type, Priority priority) {
		try {
			Ticket ticket = model.createNewTicket(reporter, topic, description, type, priority);
			mainFrame.showTicketDetails(ticket);
		} catch (TicketException e) {
			mainFrame.showErrorDialog("Invalid status change", e);
		}
	}

	public void refreshTicketList() {
		// FIXME
	}

	public void triggerApplicationShutdown() {
		connector.triggerShutdown();
		mainFrame.dispose();
	}
}
