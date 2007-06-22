jm_kpss <- function (y, ltrunc = NULL) 
{
    tiempo <- c(1:length(y))
     if (class(ltrunc) == "NULL") 
        ltrunc <- as.integer(3 * sqrt(length(y))/13)
    lmkpss <- lm(y ~ tiempo)
    ehat <- residuals(lmkpss)
    Sa <- cumsum(ehat)
    N <- length(ehat)
    if (ltrunc == 0) 
        s.2a <- 1/N * sum(ehat^2)
    if (ltrunc > 0) {
        auxa <- c(1:ltrunc)
        for (i in 1:ltrunc) auxa[i] <- (1 - i/(ltrunc + 1)) * 
            sum(ehat[(i + 1):N] * ehat[1:(N - i)])
        s.2a <- (1/N) * sum(ehat^2) + (2/N) * sum(auxa)
    }
    Trend <- N^(-2) * sum(Sa^2/s.2a)
    y2 <- y - mean(na.omit(y))
    Sb <- cumsum(y2)
    if (ltrunc == 0) 
        s.2b <- 1/N * sum(y2[1:N]^2)
    if (ltrunc > 0) {
        auxb <- c(1:ltrunc)
        for (i in 1:ltrunc) auxb[i] <- (1 - i/(ltrunc + 1)) * 
            sum(y2[(i + 1):N] * y2[1:(N - i)])
        s.2b <- (1/N) * sum(y2[1:N]^2) + (2/N) * sum(auxb)
    }
    Level <- N^(-2) * sum(Sb[1:N]^2/s.2b)
    return (list("levelst" = Level, "trendst" = Trend))
}
