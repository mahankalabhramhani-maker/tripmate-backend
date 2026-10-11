package com.example.tripmate.config;

import com.example.tripmate.entity.Destination;
import com.example.tripmate.entity.Place;
import com.example.tripmate.entity.User;
import com.example.tripmate.repository.DestinationRepository;
import com.example.tripmate.repository.PlaceRepository;
import com.example.tripmate.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DestinationRepository destinationRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(DestinationRepository destinationRepository,
                           PlaceRepository placeRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.destinationRepository = destinationRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Demo User
        if (!userRepository.existsByEmail("demo@tripmate.com")) {
            User demo = new User("John Doe", "demo@tripmate.com", passwordEncoder.encode("password123"));
            userRepository.save(demo);
        }

        // 2. Seed Destinations and Places if empty
        if (destinationRepository.count() == 0) {
            // GOA
            Destination goa = destinationRepository.save(new Destination(
                    "Goa",
                    "India",
                    "Famous for its sun-kissed golden beaches, Portuguese colonial architecture, vibrant shacks, and seafood.",
                    18000.0,
                    "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Baga Beach", "Lively beach renowned for water sports, beach shacks, and vibrant nightlife.", "Beach", "North Goa", 500.0, goa, "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Calangute Beach", "The Queen of Beaches with bustling flea markets and water parasailing.", "Beach", "North Goa", 400.0, goa, "https://images.unsplash.com/photo-1519046904884-53103b34b206?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Anjuna Beach", "Famous for its red laterite cliffs, trance sunset parties, and Wednesday Flea Market.", "Beach", "North Goa", 350.0, goa, "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Fort Aguada", "17th-century Portuguese fortress overlooking the Arabian Sea with a historic lighthouse.", "Historical", "Candolim, Goa", 100.0, goa, "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Basilica of Bom Jesus", "UNESCO World Heritage Roman Catholic basilica holding the mortal remains of St. Francis Xavier.", "Religious", "Old Goa", 50.0, goa, "https://images.unsplash.com/photo-1548013146-72479768bada?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Dudhsagar Waterfalls", "Four-tiered majestic waterfall in the Western Ghats surrounded by lush deciduous forests.", "Adventure", "Sonaulim, Goa", 800.0, goa, "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80"));

            // HYDERABAD
            Destination hyderabad = destinationRepository.save(new Destination(
                    "Hyderabad",
                    "India",
                    "City of Pearls and Nizams, blending majestic 400-year-old heritage monuments with modern tech hubs and world-famous biryani.",
                    14000.0,
                    "https://images.unsplash.com/photo-1605649487212-47bdab064df8?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Charminar", "Iconic 16th-century four-minaret mosque and symbol of Hyderabad situated in the old city.", "Historical", "Old City, Hyderabad", 50.0, hyderabad, "https://images.unsplash.com/photo-1588416936097-41850ab3d86d?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Golconda Fort", "Magnificent citadel famous for its acoustic engineering, royal palaces, and diamond vaults.", "Historical", "Golconda, Hyderabad", 80.0, hyderabad, "https://images.unsplash.com/photo-1628155930542-3c7a64e2c833?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Hussain Sagar", "Sprawling heart-shaped lake featuring a giant monolithic statue of Gautama Buddha on Gibraltar Rock.", "Nature", "Necklace Road, Hyderabad", 150.0, hyderabad, "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Ramoji Film City", "World's largest integrated film studio complex offering themed sets, stunt shows, and adventure parks.", "Entertainment", "Hayathnagar, Hyderabad", 1450.0, hyderabad, "https://images.unsplash.com/photo-1518173946687-a4c8a383392e?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Salar Jung Museum", "One of the largest art museums in India showcasing royal antiques, clock rooms, and sculptures.", "Historical", "Darulshifa, Hyderabad", 50.0, hyderabad, "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7?auto=format&fit=crop&w=800&q=80"));

            // JAIPUR
            Destination jaipur = destinationRepository.save(new Destination(
                    "Jaipur",
                    "India",
                    "The famed Pink City of Rajasthan filled with grand hill forts, ornate royal palaces, vibrant bazaars, and rich Rajput culture.",
                    16000.0,
                    "https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Amber Fort", "Opulent hilltop fort crafted from yellow and pink sandstone featuring the exquisite Sheesh Mahal (Mirror Palace).", "Historical", "Amer, Jaipur", 200.0, jaipur, "https://images.unsplash.com/photo-1603288967750-f86a51d8db00?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Hawa Mahal", "Palace of Winds with 953 honeycomb intricately carved windows designed for royal ladies to observe street life.", "Historical", "Badi Choupad, Jaipur", 100.0, jaipur, "https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("City Palace", "Stunning royal palace complex with courtyards, gardens, and museum halls displaying royal garments and armory.", "Historical", "Tulsi Marg, Jaipur", 300.0, jaipur, "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Jantar Mantar", "UNESCO World Heritage 18th-century astronomical observatory featuring the world's largest stone sundial.", "Historical", "Gangori Bazaar, Jaipur", 200.0, jaipur, "https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Johari Bazaar", "Vibrant traditional shopping market famed for authentic Kundan jewelry, Rajasthani lehengas, and handicrafts.", "Shopping", "Johari Bazaar, Jaipur", 250.0, jaipur, "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80"));

            // MANALI
            Destination manali = destinationRepository.save(new Destination(
                    "Manali",
                    "India",
                    "Enchanting Himalayan hill retreat nestled in the Kullu Valley, offering snow peaks, pine forests, hot springs, and adventure sports.",
                    20000.0,
                    "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Solang Valley", "Adventure haven for paragliding, zorbing, skiing, and panoramic views of snow-capped peaks.", "Adventure", "Solang Valley, Manali", 1200.0, manali, "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Rohtang Pass", "High mountain pass at 13,058 ft connecting Kullu with Lahaul and Spiti Valleys, with snowy slopes.", "Adventure", "Rohtang, Manali", 800.0, manali, "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Hadimba Temple", "Unique 16th-century wooden pagoda temple set inside serene cedar (Deodar) forests.", "Religious", "Dhungri, Manali", 50.0, manali, "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Jogini Waterfall", "Scenic cascading waterfall reachable via a peaceful nature trek passing apple orchards.", "Nature", "Vashisht, Manali", 100.0, manali, "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80"));

            // KASHMIR
            Destination kashmir = destinationRepository.save(new Destination(
                    "Kashmir",
                    "India",
                    "Paradise on Earth renowned for Dal Lake shikara rides, saffron fields, snow-laden slopes of Gulmarg, and warm hospitality.",
                    25000.0,
                    "https://images.unsplash.com/photo-1598091383021-15ddea10925d?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Dal Lake", "Mesmerizing lake famous for wooden carved houseboats and tranquil morning Shikara boat rides.", "Nature", "Srinagar, Kashmir", 700.0, kashmir, "https://images.unsplash.com/photo-1598091383021-15ddea10925d?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Gulmarg Gondola", "Asia's highest and longest cable car ride offering breathtaking panoramas of the Apharwat Peak.", "Adventure", "Gulmarg, Kashmir", 1600.0, kashmir, "https://images.unsplash.com/photo-1518173946687-a4c8a383392e?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Betaab Valley", "Lush picturesque green valley surrounded by snow mountains, pine trees, and the Lidder river.", "Nature", "Pahalgam, Kashmir", 150.0, kashmir, "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?auto=format&fit=crop&w=800&q=80"));

            // MUMBAI
            Destination mumbai = destinationRepository.save(new Destination(
                    "Mumbai",
                    "India",
                    "The City of Dreams and Financial Capital of India, home to Bollywood, coastal promenades, and historic Victorian Gothic architecture.",
                    22000.0,
                    "https://images.unsplash.com/photo-1567157577867-05ccb1388e66?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Gateway of India", "Grand 20th-century arch monument overlooking the Arabian Sea in Apollo Bunder.", "Historical", "Colaba, Mumbai", 50.0, mumbai, "https://images.unsplash.com/photo-1567157577867-05ccb1388e66?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Marine Drive", "3.6-kilometer-long arc shaped coastal boulevard fondly known as the Queen's Necklace.", "Beach", "South Mumbai", 50.0, mumbai, "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Elephanta Caves", "UNESCO World Heritage rock-cut cave temples dedicated to Lord Shiva on Elephanta Island.", "Historical", "Gharapuri, Mumbai", 250.0, mumbai, "https://images.unsplash.com/photo-1548013146-72479768bada?auto=format&fit=crop&w=800&q=80"));

            // DELHI
            Destination delhi = destinationRepository.save(new Destination(
                    "Delhi",
                    "India",
                    "The National Capital of India, overflowing with ancient Mughal bastions, British Raj boulevards, and sensory street food markets.",
                    17000.0,
                    "https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Red Fort (Lal Qila)", "Historic red sandstone fortress complex that served as the main residence of the Mughal Emperors.", "Historical", "Old Delhi", 80.0, delhi, "https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Qutub Minar", "73-meter-tall victory minaret and UNESCO World Heritage site built in 1192 with red sandstone.", "Historical", "Mehrauli, New Delhi", 80.0, delhi, "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("India Gate", "War memorial triumphal arch standing at the Rajpath commemorating Indian soldiers.", "Historical", "Rajpath, New Delhi", 50.0, delhi, "https://images.unsplash.com/photo-1588416936097-41850ab3d86d?auto=format&fit=crop&w=800&q=80"));

            // BANGALORE
            Destination bangalore = destinationRepository.save(new Destination(
                    "Bangalore",
                    "India",
                    "The Silicon Valley and Garden City of India, known for pleasant year-round weather, lush parks, craft breweries, and tech innovation.",
                    18000.0,
                    "https://images.unsplash.com/photo-1596176530529-78163a4f7af2?auto=format&fit=crop&w=1200&q=80"
            ));

            placeRepository.save(new Place("Lalbagh Botanical Garden", "240-acre botanical garden featuring over 1,800 species of plants and a famous London-inspired Glass House.", "Nature", "Mavalli, Bangalore", 50.0, bangalore, "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Bangalore Palace", "Tudor-style royal palace featuring fortified towers, stained glass windows, and lush green lawns.", "Historical", "Vasanth Nagar, Bangalore", 250.0, bangalore, "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=800&q=80"));
            placeRepository.save(new Place("Commercial Street", "Bustling shopping hub renowned for apparel, jewelry, spices, and vibrant cafes.", "Shopping", "Tasker Town, Bangalore", 300.0, bangalore, "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80"));
        }
    }
}
