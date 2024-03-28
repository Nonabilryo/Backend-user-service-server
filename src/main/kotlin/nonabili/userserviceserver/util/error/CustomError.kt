package nonabili.userserviceserver.util.error

class CustomError(val reason: ErrorState): RuntimeException(reason.message)