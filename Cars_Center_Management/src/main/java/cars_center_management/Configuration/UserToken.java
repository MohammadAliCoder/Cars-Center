package cars_center_management.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserToken {
    private final String Sub="sub";
    private final String Cre="created";
    private Long Token_Validity=604800L;
    private String Token_SECRET="CarsYoussef";//للتشفير

    public String generateToken(UserDetails userDetails){
        Map<String,Object> map=new HashMap<>();
        map.put(Sub,userDetails.getUsername());
        map.put(Cre,new Date());


        return Jwts.builder().setClaims(map)
                .setExpiration(generateTokenDate())
                .signWith(SignatureAlgorithm.ES512,Token_SECRET).compact();
    }
    public String getUsernameFromToken(String token ){
        try {
            Claims claims= getClaims(token);
            return claims.getSubject();
        }catch (Exception e){
            return null;
        }
    }

    private Date generateTokenDate() {
        return new Date(System.currentTimeMillis()+Token_Validity*1000);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username=getUsernameFromToken(token);
        return ((userDetails.getUsername().equals(username)) && isTokenExpiration(token));
    }

    private boolean isTokenExpiration(String token) {
        Date expiration=getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token){
        try {
            Claims claims= Jwts.parser().setSigningKey(Token_SECRET).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }
}
