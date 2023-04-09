package com.example.sockswarehouse.service;

import com.example.sockswarehouse.entity.Socks;
import com.example.sockswarehouse.operation.Operation;
import com.example.sockswarehouse.repositoy.SocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocksService {
    private final Logger logger = LoggerFactory.getLogger(SocksService.class);

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    /**
     * Метод прихода носков.
     * <br>
     * Используется метод репозитория {@link com.example.sockswarehouse.repositoy.SocksRepository#findSocksByColorAndCottonPart(String, Integer)}
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param color   цвет носков
     * @param cottonPart количество хлопка
     * @param quantity количество носков в приход
     * @return фиксация прихода
     */
    public Socks incomeSocks(String color, Integer cottonPart, int quantity) {
        logger.info("Invoke method addSocks");
        if (socksRepository.findSocksByColorAndCottonPart(color, cottonPart) == null) {
            Socks socks = new Socks();
            socks.setColor(color);
            socks.setCottonPart(cottonPart);
            socks.setQuantity(quantity);
            return socksRepository.save(socks);
        } else {
            Socks socks = socksRepository.findSocksByColorAndCottonPart(color, cottonPart);
            int numbers = socks.getQuantity();
            socks.setQuantity(socks.getQuantity() + numbers);
            return socksRepository.save(socks);
        }
    }

    /**
     * Метод отпуска носков.
     * <br>
     * Используется метод репозитория {@link com.example.sockswarehouse.repositoy.SocksRepository#findSocksByColorAndCottonPartEquals(String, Integer)}
     * Используется метод репозитория {@link com.example.sockswarehouse.repositoy.SocksRepository#findSocksByColorAndCottonPartLessThan(String, Integer)} 
     * Используется метод репозитория {@link com.example.sockswarehouse.repositoy.SocksRepository#findSocksByColorAndCottonPartGreaterThan(String, Integer)} 
     *
     * @param color   цвет носков
     * @param operation значение запроса по составу хлопка
     * @param cottonPart количество хлопка
     * @return получение количества носков по заданным параметрам
     */
    public List<Socks> getSocks(String color, Operation operation, Integer cottonPart) {
        logger.info("Invoke method getSocks");
        List<Socks> findSocks = new ArrayList<>();
        switch (operation) {
            case equal -> findSocks = socksRepository.findSocksByColorAndCottonPartEquals(color, cottonPart);
            case lessThan -> findSocks = socksRepository.findSocksByColorAndCottonPartLessThan(color, cottonPart);
            case moreThan -> findSocks = socksRepository.findSocksByColorAndCottonPartGreaterThan(color, cottonPart);
        }
        return findSocks;
    }

    /**
     * Метод отпуска носков.
     * <br>
     * Используется метод репозитория {@link com.example.sockswarehouse.repositoy.SocksRepository#findSocksByColorAndCottonPart(String, Integer)}
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#delete(Object)}
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param color   цвет носков
     * @param cottonPart количество хлопка
     * @param quantity количество носков в отпуск
     * @return фиксация отпуска
     */
    public Socks outcomeSocks(String color, Integer cottonPart, int quantity) {
        logger.info("Invoke method outcomeSocks");
        Socks socks = socksRepository.findSocksByColorAndCottonPart(color, cottonPart);
        int numbersSocks = socks.getQuantity();
        if (numbersSocks < quantity) {
            throw new ArithmeticException("This quantity is not in stock");
        } else if (numbersSocks == quantity) {
            socksRepository.delete(socks);
        } else {
            socks.setQuantity(numbersSocks - quantity);
        }
        return socksRepository.save(socks);
    }
}