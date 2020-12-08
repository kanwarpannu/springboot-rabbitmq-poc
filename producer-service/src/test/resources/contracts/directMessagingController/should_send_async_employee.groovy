package contracts.directMessagingController

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label("direct-async-send-employee")
    input {
        triggeredBy "queryAsyncEmployee()"
    }
    outputMessage {
        sentTo "direct.exchange.async"
        headers {
            messagingContentType(applicationJson())
        }
        body([
                employeeId : $(stub(new Random().nextInt(1000000) + 1000000), test(anyNumber())),
                firstName  : $(stub("First Name"), test(anyNonEmptyString())),
                lastName   : $(stub("Last Name"), test(anyNonEmptyString())),
                phoneNumber: $(stub("123123123"), test(anyNumber()))
        ])
    }
}
