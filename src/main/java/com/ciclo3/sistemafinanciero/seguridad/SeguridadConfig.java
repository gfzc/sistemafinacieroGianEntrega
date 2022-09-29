package com.ciclo3.sistemafinanciero.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAutenticacion(AuthenticationManagerBuilder autenticacion) throws Exception{
        autenticacion.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select email,password,estado from empleado where email=?")
                .authoritiesByUsernameQuery("select email,rol from empleado where email=?");

    }

    //Metodo para permitiri logue y deslogueo a usuarios autorizados
    protected void configuracion(HttpSecurity http)throws Exception{
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();


    }




}
