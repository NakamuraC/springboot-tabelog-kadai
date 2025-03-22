package com.example.nagoyameshi.service;

import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripeService {
    @Value("${stripe.api-key}")
    private String stripeApiKey;
    private final UserService userService; // これを追加

    public StripeService(UserService userService) { // コンストラクタにUserServiceを追加
        this.userService = userService;
    }

    public String createStripeSession(HttpServletRequest httpServletRequest, User user) {
        Stripe.apiKey = stripeApiKey;
        String requestUrl = new String(httpServletRequest.getRequestURL());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("有料プラン (月額)")
                                                                .build())
                                                .setUnitAmount(300L)
                                                .setCurrency("jpy")
                                                .setRecurring(
                                                        SessionCreateParams.LineItem.PriceData.Recurring.builder()
                                                                .setInterval(
                                                                        SessionCreateParams.LineItem.PriceData.Recurring.Interval.MONTH)
                                                                .build())
                                                .build())
                                .setQuantity(1L)
                                .build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(requestUrl.replaceAll("/subscription/register", "") + "/?reserved")
                .setCancelUrl(requestUrl.replace("/subscription/register", ""))
                .putMetadata("userId", String.valueOf(user.getId()))
                .build();
        try {
            Session session = Session.create(params);
            return session.getId();
        } catch (StripeException e) {
            System.err.println("Stripeエラー: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public void processSessionCompleted(Event event) { // 折りたたみエラー修正
        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
        optionalStripeObject.ifPresent(stripeObject -> {
            Session session = (Session) stripeObject;

            Map<String, String> metadata = session.getMetadata();
            userService.upgradeRole(metadata);
        });
    }
}