package dbfacades;

import entity.Customer;
import entity.ItemType;
import entity.OrderExamprep;
import entity.OrderLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
Simple Facade demo for this start-up project
 Use this in your own project by:
  - Rename this class to reflect your "business logic"
  - Delete the class entity.Car, and add your own entity classes
  - Delete the three public methods below, and replace with your own Facade Logic 
  - Delete all content in the main method

 */
public class OrderFacade {

    EntityManagerFactory emf;

    public OrderFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Customer customer = new Customer("mark", "testing@mdd.com");
        OrderExamprep order = new OrderExamprep();
        OrderExamprep order1 = new OrderExamprep();
        OrderExamprep order2 = new OrderExamprep();
        OrderExamprep order3 = new OrderExamprep();
        OrderExamprep order4 = new OrderExamprep();

        OrderLine ol = new OrderLine(25);
        OrderLine o2 = new OrderLine(15);
        OrderLine o3 = new OrderLine(55);
        OrderLine o4 = new OrderLine(51);

        ItemType it1 = new ItemType();
        ItemType it2 = new ItemType();
        ItemType it3 = new ItemType();
        ItemType it4 = new ItemType();
        it1.setName("IRONBALL");
        it1.setPrice(32.5);

        it2.setName("jernkule");
        it2.setPrice(15.0);

        it3.setName("stål");
        it3.setPrice(7.0);

        it4.setName("en kat");
        it4.setPrice(700.0);

        ol.setOrder(order);
        o2.setOrder(order);
        o3.setOrder(order);
        o4.setOrder(order);

        customer.addOrder(order);
        customer.addOrder(order1);
        customer.addOrder(order2);
        customer.addOrder(order3);
        customer.addOrder(order4);

        order.setCustomer(customer);
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        order3.setCustomer(customer);
        order4.setCustomer(customer);

        order.addOrderline(ol);
        order.addOrderline(o2);
        order.addOrderline(o3);
        order.addOrderline(o4);

        order1.addOrderline(o2);
        order1.addOrderline(o3);

        ol.setItem(it1);
        o2.setItem(it2);
        o3.setItem(it3);
        o4.setItem(it4);
        /*
            em.persist(order);
            em.persist(ol);
            em.persist(it1);
         */

        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Customer CreateACustomer(String name, String email) {
        EntityManager em = emf.createEntityManager();
        Customer customer = new Customer(name, email);
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return customer;
    }

    public Customer findACustomer(String email /*Correct parameter?*/) {
        EntityManager em = emf.createEntityManager();
        try {
            return (Customer) em.createQuery("SELECT a FROM Customer a WHERE a.email = :email")
                    .setParameter("email", email).getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Customer>) em.createQuery("SELECT c FROM Customer c").getResultList();
        } finally {
            em.close();
        }
    }

    public OrderExamprep createAnOrder(OrderExamprep order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return order;

    }

    public Customer addOrderToCustomer(OrderExamprep order, String email) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = (Customer) em.createQuery("SELECT a FROM Customer a WHERE a.email = :email")
                    .setParameter("email", email).getSingleResult();

            em.getTransaction().begin();
            customer.addOrder(order);
            order.setCustomer(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }

    }

    public OrderExamprep findAnOrder(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(OrderExamprep.class, id);
        } finally {
            em.close();
        }

    }

    public List<OrderExamprep> findAllOfCustomersOrders(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<OrderExamprep>) 
                    em.createQuery("SELECT c FROM OrderExamprep c WHERE c.customer.email = :email")
                            .setParameter("email", email).getResultList();
            
            
        } finally {
            em.close();
        }
        
    }

    

    public OrderExamprep addOrderLineToOrder(int quantity, Long orderId) {
        EntityManager em = emf.createEntityManager();
        try {
            OrderExamprep order = (OrderExamprep) em.createQuery("SELECT c FROM OrderExamprep c WHERE c.id = :orderId")
                    .setParameter("orderId", orderId).getSingleResult();
            
            em.getTransaction().begin();
            order.addOrderline(new OrderLine(quantity));
            em.getTransaction().commit();
            return order;
        } finally {
            em.close();
        }
    }
    
    
}
