--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5 (Debian 11.5-2)
-- Dumped by pg_dump version 11.5 (Debian 11.5-2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: au_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.au_role (
    id bigint NOT NULL,
    role_code character varying(255),
    uuid character varying(255)
);


--
-- Name: au_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.au_user (
    id bigint NOT NULL,
    email_address character varying(255),
    password_hash character varying(255),
    real_name character varying(255),
    user_name character varying(255),
    uuid character varying(255)
);


--
-- Name: au_user_role_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.au_user_role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: au_user_roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.au_user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- Name: au_user_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.au_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: au_user_token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.au_user_token (
    id bigint NOT NULL,
    token character varying(255),
    uuid character varying(255),
    valid_to timestamp without time zone,
    user_id bigint
);


--
-- Name: au_user_token_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.au_user_token_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_connection_descriptor; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_connection_descriptor (
    id bigint NOT NULL,
    dbname character varying(255),
    host character varying(255),
    password character varying(255),
    port character varying(255),
    username character varying(255),
    uuid character varying(255),
    rs_driver_id bigint
);


--
-- Name: rs_dashboard; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_dashboard (
    id bigint NOT NULL,
    uuid character varying(255),
    user_id bigint
);


--
-- Name: rs_dashboard_query; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_dashboard_query (
    id bigint NOT NULL,
    is_chart boolean,
    query_order integer,
    uuid character varying(255),
    dashboard_id bigint,
    query_id bigint
);


--
-- Name: rs_dashboard_query_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_dashboard_query_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_dashboard_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_dashboard_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_driver; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_driver (
    id bigint NOT NULL,
    db_type character varying(255),
    driver_class_name character varying(255),
    name character varying(255),
    uuid character varying(255)
);


--
-- Name: rs_driver_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_driver_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_query; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_query (
    id bigint NOT NULL,
    connection_uuid character varying(255),
    query_name character varying(255),
    query_string character varying(255),
    uuid character varying(255),
    visibility character varying(255),
    creator_user_id bigint
);


--
-- Name: rs_query_chart; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_query_chart (
    id bigint NOT NULL,
    chart_type character varying(255),
    uuid character varying(255),
    data_column_id bigint,
    label_column_id bigint,
    query_id bigint
);


--
-- Name: rs_query_chart_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_query_chart_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_query_column; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_query_column (
    id bigint NOT NULL,
    column_name character varying(255),
    column_type character varying(255),
    uuid character varying(255),
    query_id bigint
);


--
-- Name: rs_query_column_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_query_column_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_query_execution; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_query_execution (
    id bigint NOT NULL,
    execution_data text,
    execution_time timestamp without time zone,
    uuid character varying(255),
    query_id bigint
);


--
-- Name: rs_query_execution_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_query_execution_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_query_parameter; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_query_parameter (
    id bigint NOT NULL,
    parameter_name character varying(255),
    parameter_value character varying(255),
    uuid character varying(255),
    query_id bigint
);


--
-- Name: rs_query_parameter_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_query_parameter_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_query_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_query_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_query_teams; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_query_teams (
    query_id bigint NOT NULL,
    team_id bigint NOT NULL
);


--
-- Name: rs_team; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_team (
    id bigint NOT NULL,
    name character varying(255),
    uuid character varying(255)
);


--
-- Name: rs_team_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rs_team_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rs_user_teams; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rs_user_teams (
    team_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: au_role au_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_role
    ADD CONSTRAINT au_role_pkey PRIMARY KEY (id);


--
-- Name: au_user au_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_user
    ADD CONSTRAINT au_user_pkey PRIMARY KEY (id);


--
-- Name: au_user_roles au_user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_user_roles
    ADD CONSTRAINT au_user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: au_user_token au_user_token_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_user_token
    ADD CONSTRAINT au_user_token_pkey PRIMARY KEY (id);


--
-- Name: rs_connection_descriptor rs_connection_descriptor_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_connection_descriptor
    ADD CONSTRAINT rs_connection_descriptor_pkey PRIMARY KEY (id);


--
-- Name: rs_dashboard rs_dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_dashboard
    ADD CONSTRAINT rs_dashboard_pkey PRIMARY KEY (id);


--
-- Name: rs_dashboard_query rs_dashboard_query_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_dashboard_query
    ADD CONSTRAINT rs_dashboard_query_pkey PRIMARY KEY (id);


--
-- Name: rs_driver rs_driver_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_driver
    ADD CONSTRAINT rs_driver_pkey PRIMARY KEY (id);


--
-- Name: rs_query_chart rs_query_chart_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_chart
    ADD CONSTRAINT rs_query_chart_pkey PRIMARY KEY (id);


--
-- Name: rs_query_column rs_query_column_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_column
    ADD CONSTRAINT rs_query_column_pkey PRIMARY KEY (id);


--
-- Name: rs_query_execution rs_query_execution_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_execution
    ADD CONSTRAINT rs_query_execution_pkey PRIMARY KEY (id);


--
-- Name: rs_query_parameter rs_query_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_parameter
    ADD CONSTRAINT rs_query_parameter_pkey PRIMARY KEY (id);


--
-- Name: rs_query rs_query_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query
    ADD CONSTRAINT rs_query_pkey PRIMARY KEY (id);


--
-- Name: rs_query_teams rs_query_teams_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_teams
    ADD CONSTRAINT rs_query_teams_pkey PRIMARY KEY (query_id, team_id);


--
-- Name: rs_team rs_team_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_team
    ADD CONSTRAINT rs_team_pkey PRIMARY KEY (id);


--
-- Name: rs_user_teams rs_user_teams_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_user_teams
    ADD CONSTRAINT rs_user_teams_pkey PRIMARY KEY (team_id, user_id);


--
-- Name: au_user_roles fk1grh1tw3fm0rdkhjuyh27gg4y; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_user_roles
    ADD CONSTRAINT fk1grh1tw3fm0rdkhjuyh27gg4y FOREIGN KEY (role_id) REFERENCES public.au_role(id);


--
-- Name: rs_dashboard_query fk521s93uteujup9uycav2j8xk6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_dashboard_query
    ADD CONSTRAINT fk521s93uteujup9uycav2j8xk6 FOREIGN KEY (query_id) REFERENCES public.rs_query(id);


--
-- Name: rs_query_chart fk5mfk7qsi1okof3o5muvafwbjm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_chart
    ADD CONSTRAINT fk5mfk7qsi1okof3o5muvafwbjm FOREIGN KEY (label_column_id) REFERENCES public.rs_query_column(id);


--
-- Name: rs_user_teams fk6rc2h6l36dbjjtecyhx3hxwfx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_user_teams
    ADD CONSTRAINT fk6rc2h6l36dbjjtecyhx3hxwfx FOREIGN KEY (user_id) REFERENCES public.au_user(id);


--
-- Name: au_user_token fk7ck53tjf0r3elwqfsnu0bpmkj; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_user_token
    ADD CONSTRAINT fk7ck53tjf0r3elwqfsnu0bpmkj FOREIGN KEY (user_id) REFERENCES public.au_user(id);


--
-- Name: rs_query_teams fk7hqx67w05ohih50gke90nxyg; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_teams
    ADD CONSTRAINT fk7hqx67w05ohih50gke90nxyg FOREIGN KEY (query_id) REFERENCES public.rs_query(id);


--
-- Name: rs_query_execution fk7u8em633kro5ktv9nk3lakip1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_execution
    ADD CONSTRAINT fk7u8em633kro5ktv9nk3lakip1 FOREIGN KEY (query_id) REFERENCES public.rs_query(id);


--
-- Name: rs_dashboard fk8dbav9akcys3xl9kg6opvdwdf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_dashboard
    ADD CONSTRAINT fk8dbav9akcys3xl9kg6opvdwdf FOREIGN KEY (user_id) REFERENCES public.au_user(id);


--
-- Name: rs_dashboard_query fk8itm3whbpnwb1x7jfcvk31kan; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_dashboard_query
    ADD CONSTRAINT fk8itm3whbpnwb1x7jfcvk31kan FOREIGN KEY (dashboard_id) REFERENCES public.rs_dashboard(id);


--
-- Name: rs_query_parameter fkhsvsbnj20o5ygew7cc2loou5w; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_parameter
    ADD CONSTRAINT fkhsvsbnj20o5ygew7cc2loou5w FOREIGN KEY (query_id) REFERENCES public.rs_query(id);


--
-- Name: au_user_roles fkjlkhwqov496gsojef5fyyofc3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.au_user_roles
    ADD CONSTRAINT fkjlkhwqov496gsojef5fyyofc3 FOREIGN KEY (user_id) REFERENCES public.au_user(id);


--
-- Name: rs_query_column fkjwamx7w969ai6o5pq6rnay1lf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_column
    ADD CONSTRAINT fkjwamx7w969ai6o5pq6rnay1lf FOREIGN KEY (query_id) REFERENCES public.rs_query(id);


--
-- Name: rs_query_teams fknchvxrov7hjat74fbhl28k0g4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_teams
    ADD CONSTRAINT fknchvxrov7hjat74fbhl28k0g4 FOREIGN KEY (team_id) REFERENCES public.rs_team(id);


--
-- Name: rs_query_chart fknine88tnae28x94sm4077yeuy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_chart
    ADD CONSTRAINT fknine88tnae28x94sm4077yeuy FOREIGN KEY (query_id) REFERENCES public.rs_query(id);


--
-- Name: rs_query fkpdure7vy1unlawhhsgqe40y3q; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query
    ADD CONSTRAINT fkpdure7vy1unlawhhsgqe40y3q FOREIGN KEY (creator_user_id) REFERENCES public.au_user(id);


--
-- Name: rs_user_teams fkqckors59ppnpqe745x5n434kh; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_user_teams
    ADD CONSTRAINT fkqckors59ppnpqe745x5n434kh FOREIGN KEY (team_id) REFERENCES public.rs_team(id);


--
-- Name: rs_query_chart fkrjc8shp011muyu9tpuhq3dy34; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_query_chart
    ADD CONSTRAINT fkrjc8shp011muyu9tpuhq3dy34 FOREIGN KEY (data_column_id) REFERENCES public.rs_query_column(id);


--
-- Name: rs_connection_descriptor fksq1hfjydlabatjcilbdmp2s9v; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rs_connection_descriptor
    ADD CONSTRAINT fksq1hfjydlabatjcilbdmp2s9v FOREIGN KEY (rs_driver_id) REFERENCES public.rs_driver(id);


--
-- PostgreSQL database dump complete
--