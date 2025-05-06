    package JSR.To_Do_List.Config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import JSR.To_Do_List.Services.CustomUserDetailsService;
    import org.springframework.security.config.http.SessionCreationPolicy;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;

        @Autowired
        public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
            this.customUserDetailsService = customUserDetailsService;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/signup", "/login", "/css/**", "/js/**").permitAll()
                    .requestMatchers("/customers").authenticated() // Ensure /customers is secured
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")                      // Custom login page
                    .loginProcessingUrl("/login")             // POST request URL for login form
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/customers", true)    // Redirect after successful login
                    .failureUrl("/login?error")               // Redirect on login failure
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                )
                // Disable session creation (stateless)
                // .sessionManagement(session -> session
                //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // );

                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Use IF_REQUIRED or ALWAYS
            );

            return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
            AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        
            authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        
            return authenticationManagerBuilder.build();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(); 
        }
    }
