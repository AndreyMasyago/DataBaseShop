package DataBase;

import DataBase.Domain.*;
import DataBase.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class    InitDatabase implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(InitDatabase.class);

    SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private DeliveryContentRepository deliveryContentRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderContentRepository orderContentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private RejectRepository rejectRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private StorageTransactionsRepository storageTransactionsRepository;

    private List<String[]> readCsv(File file) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        List<String[]> rows = new ArrayList<>();

        try {
            String row = csvReader.readLine();

            // Skipping first row - should always be header
            row = csvReader.readLine();

            while (row != null) {
                String[] data = row.split(",");
                rows.add(data);
                row = csvReader.readLine();
            }

            csvReader.close();
        } catch (IOException e) {
            logger.error("Can't read csv file");
            throw e;
        }

        return rows;
    }

    private Map<String, Catalog> loadCatalog() throws IOException {

        logger.info("loading catalog");
        File csv = new ClassPathResource("init/catalog.csv", this.getClass().getClassLoader()).getFile();

        Map<String, Catalog> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            String goodsName = row[1];

            Catalog model = catalogRepository.save(new Catalog(goodsName));

            virtualMap.put(virtualId, model);
        });

        logger.info("catalog loaded");

        return virtualMap;
    }

    private Map<String, Provider> loadProviders() throws IOException {
        logger.info("loading providers");
        File csv = new ClassPathResource("init/providers.csv", this.getClass().getClassLoader()).getFile();

        Map<String, Provider> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            String providerName = row[1];
            String category = row[2];

            Provider model = providerRepository.save(new Provider(providerName, category));

            virtualMap.put(virtualId, model);
        });

        logger.info("providers loaded");

        return virtualMap;
    }

    private Map<String, Delivery> loadDeliveries() throws IOException {

        logger.info("loading deliveries");
        File csv = new ClassPathResource("init/deliveries.csv", this.getClass().getClassLoader()).getFile();

        Map<String, Delivery> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            try {
                java.sql.Date arrivingDateOnStorage = new java.sql.Date(dateParser.parse(row[1]).getTime());

                Delivery model = deliveryRepository.save(new Delivery(arrivingDateOnStorage));

                virtualMap.put(virtualId, model);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        logger.info("deliveries loaded");

        return virtualMap;
    }

    private Map<String, OrderEntity> loadOrders() throws IOException {

        logger.info("loading orders");
        File csv = new ClassPathResource("init/orders.csv", this.getClass().getClassLoader()).getFile();

        Map<String, OrderEntity> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            try {
                java.sql.Date orderDate = new java.sql.Date(dateParser.parse(row[1]).getTime());

                OrderEntity model = orderRepository.save(new OrderEntity(orderDate));

                virtualMap.put(virtualId, model);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        logger.info("orders loaded");

        return virtualMap;
    }

    private Map<String, Storage> loadStorages() throws IOException {

        logger.info("loading storages");
        File csv = new ClassPathResource("init/storages.csv", this.getClass().getClassLoader()).getFile();

        Map<String, Storage> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            int cellsSize = Integer.parseInt(row[1]);

            Storage model = storageRepository.save(new Storage(cellsSize));

            virtualMap.put(virtualId, model);
        });

        logger.info("storages loaded");

        return virtualMap;
    }

    private Map<String, Goods> loadGoods(Map<String, Catalog> catalogs, Map<String, Provider> providers) throws IOException {

        logger.info("loading goods");
        File csv = new ClassPathResource("init/goods.csv", this.getClass().getClassLoader()).getFile();

        Map<String, Goods> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            Catalog catalog = catalogs.get(row[1]);
            int size = Integer.parseInt(row[2]);
            int deliveryTime = Integer.parseInt(row[3]);
            int purchasePrice = Integer.parseInt(row[4]);
            int sellingPrice = Integer.parseInt(row[5]);
            String producer = row[6];
            Provider provider = providers.get(row[7]);

            Goods model = goodsRepository.save(new Goods(
                    catalog,
                    size,
                    deliveryTime,
                    purchasePrice,
                    sellingPrice,
                    producer,
                    provider
            ));

            virtualMap.put(virtualId, model);
        });

        logger.info("goods loaded");

        return virtualMap;
    }

    private Map<String, DeliveryContent> loadDeliveryContents(Map<String, Delivery> deliveries, Map<String, Goods> goodsMap) throws IOException {

        logger.info("loading deliveryContents");
        File csv = new ClassPathResource("init/deliveryContents.csv", this.getClass().getClassLoader()).getFile();

        Map<String, DeliveryContent> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            Delivery delivery = deliveries.get(row[1]);
            int amount = Integer.parseInt(row[2]);
            Goods goods = goodsMap.get(row[3]);

            DeliveryContent model = deliveryContentRepository.save(new DeliveryContent(
                    delivery,
                    amount,
                    goods
            ));

            virtualMap.put(virtualId, model);
        });

        logger.info("deliveryContents loaded");

        return virtualMap;
    }

    private Map<String, OrderContent> loadOrderContents(Map<String, Goods> goodsMap, Map<String, OrderEntity> orders) throws IOException {

        logger.info("loading orderContents");
        File csv = new ClassPathResource("init/orderContents.csv", this.getClass().getClassLoader()).getFile();

        Map<String, OrderContent> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            Goods goods = goodsMap.get(row[1]);
            OrderEntity order = orders.get(row[2]);
            int amount = Integer.parseInt(row[3]);

            OrderContent model = orderContentRepository.save(new OrderContent(
                    goods,
                    amount,
                    order
            ));

            virtualMap.put(virtualId, model);
        });

        logger.info("orderContents loaded");

        return virtualMap;
    }

    private Map<String, Reject> loadRejects(Map<String, Goods> goodsMap, Map<String, OrderEntity> orders) throws IOException {

        logger.info("loading rejects");
        File csv = new ClassPathResource("init/rejects.csv", this.getClass().getClassLoader()).getFile();

        Map<String, Reject> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            Goods goods = goodsMap.get(row[1]);
            OrderEntity order = orders.get(row[2]);
            int amount = Integer.parseInt(row[3]);

            Reject model = rejectRepository.save(new Reject(
                    goods,
                    amount,
                    order
            ));

            virtualMap.put(virtualId, model);
        });

        logger.info("rejects loaded");

        return virtualMap;
    }

    private Map<String, StorageTransactions> loadStorageTransactions(Map<String, Goods> goodsMap, Map<String, Storage> storages) throws IOException {

        logger.info("loading storageTransactions");
        File csv = new ClassPathResource("init/storageTransactions.csv", this.getClass().getClassLoader()).getFile();

        Map<String, StorageTransactions> virtualMap = new HashMap<>();
        List<String[]> rows = readCsv(csv);

        rows.forEach(row -> {
            String virtualId = row[0];
            Goods goods = goodsMap.get(row[1]);
            int amount = Integer.parseInt(row[2]);
            Storage storage = storages.get(row[3]);

            try {
                java.sql.Date transactionDate = new java.sql.Date(dateParser.parse(row[4]).getTime());

                StorageTransactions model = storageTransactionsRepository.save(new StorageTransactions(
                        goods,
                        amount,
                        storage,
                        transactionDate
                ));

                virtualMap.put(virtualId, model);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        logger.info("storageTransactions loaded");

        return virtualMap;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
//        catalogRepository.deleteAll();
//        providerRepository.deleteAll();
//        deliveryRepository.deleteAll();
//        orderRepository.deleteAll();
//        storageRepository.deleteAll();
//        goodsRepository.deleteAll();
//        deliveryContentRepository.deleteAll();
//        orderContentRepository.deleteAll();
//        rejectRepository.deleteAll();
//        storageTransactionsRepository.deleteAll();

        if (catalogRepository.count() > 0) {
            logger.info("DB is already populated, skipping");
            return;
        }

        Map<String, Catalog> catalog = this.loadCatalog();

        Map<String, Provider> providers = this.loadProviders();

        Map<String, Delivery> deliveries = this.loadDeliveries();

        Map<String, OrderEntity> orders = this.loadOrders();

        Map<String, Storage> storages = this.loadStorages();

        Map<String, Goods> goods = this.loadGoods(catalog, providers);

        Map<String, DeliveryContent> deliveryContents = this.loadDeliveryContents(deliveries, goods);

        Map<String, OrderContent> orderContents = this.loadOrderContents(goods, orders);

        Map<String, Reject> rejects = this.loadRejects(goods, orders);

        Map<String, StorageTransactions> storageTransactions = this.loadStorageTransactions(goods, storages);
    }
}
