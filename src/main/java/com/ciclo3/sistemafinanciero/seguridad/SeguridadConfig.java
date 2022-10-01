package com.ciclo3.sistemafinanciero.seguridad;

import com.ciclo3.sistemafinanciero.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    public void configAutenticacion(AuthenticationManagerBuilder autenticacion) throws Exception{
        autenticacion.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select email,password,estado from empleado where email=?")
                .authoritiesByUsernameQuery("select email,rol from empleado where email=?");

    }

    //Metodo para permitiri logue y deslogueo a usuarios autorizados
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()
                .antMatchers("/", "/VerEmpresas/**").hasRole("ADMIN")
                .antMatchers("/VerEmpleados/**").hasRole("ADMIN")
                .antMatchers("/Empresas/**").hasRole("ADMIN")
                .antMatchers("/Empleado/**").hasRole("ADMIN")
                .antMatchers("/VerMovimientos/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/AgregarMovimiento/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/EditarMovimiento/**").hasAnyRole("ADMIN", "USER")
                .and().formLogin().successHandler(customSuccessHandler)
                .and().exceptionHandling().accessDeniedPage("/Denegado")
                .and().logout().permitAll();


    }




}
