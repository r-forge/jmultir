lagn <- function(y, k, startLag=1)
{
  if (startLag > k)
    stop("'startLag' must be <= k")
  if (startLag < 0)
    stop("'startLag' must be >= 0")
  
  N <- length(y)
  
  if (k == 0) return(matrix(y))
  
  
  if (k >= N)
    stop("too many lags for given sample size")
  
  
  y.r <- matrix(NA, nrow=N, ncol=k+1-startLag);
  for(i in startLag:k) {
    y.r[(i+1):N, i-startLag+1] <- y[1:(N-i)]
  }
  return(y.r)
}
