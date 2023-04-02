package com.example.bankomat.service;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.KartaMuddat;
import com.example.bankomat.entity.KartaTuri;
import com.example.bankomat.entity.Manzil;
import com.example.bankomat.entity.bankomat.*;
import com.example.bankomat.entity.enums.MijozStatus;
import com.example.bankomat.entity.karta.Karta;
import com.example.bankomat.entity.karta.Users;
import com.example.bankomat.entity.template.RoleConstanta;
import com.example.bankomat.payload.*;
import com.example.bankomat.repository.*;
import com.example.bankomat.token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ServiceImplement implements LoyihalashService, UserDetailsService {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    ManzilRepository manzilRepository;
    @Autowired
    KartaTuriRepository kartaTuriRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    KartaRepository kartaRepository;
    @Autowired
    KartaMuddatRepository kartaMuddatRepository;
    @Autowired
    XodimRepository xodimRepository;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    KomissiyaRepository komissiyaRepository;
    @Autowired
    BankomatKartaTuriRepository bankomatKartaTuriRepository;
    @Autowired
    KupyuraRepository kupyuraRepository;
    @Autowired
    BankomatRepository bankomatRepository;
    @Autowired
    HisobotRepository hisobotRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        Optional<Xodim> byUserName = xodimRepository.findByUsername(userName);
        if (byUserName.isPresent()){
            return byUserName.get();
        }
        throw new UsernameNotFoundException("Bunday foydalanuvchi topilmadi");
    }

    @Override
    public ApiRespons addkartaturi(KartaTuri kartaTuri) {
        boolean b = kartaTuriRepository.existsByNomi(kartaTuri.getNomi());
        if (!b){
            KartaTuri kartaTuri1 = new KartaTuri(
                    kartaTuri.getNomi(),
                    kartaTuri.getTurKodi()
            );
            kartaTuriRepository.save(kartaTuri1);
            return new ApiRespons("Karta turi muvaffaqiatli saqlandi!",true);
        }
        return new ApiRespons("Bunday karta turi mavjud!",false);
    }

    @Override
    public ApiRespons addkartamuddat(Integer bankId, Integer turId, KartaMuddatDto kartaMuddatDto) {
        boolean b = kartaMuddatRepository.existsByBankIdAndKartaTuriIdAndKartaMuddat(bankId, turId, kartaMuddatDto.getKartaMuddat());
        if (!b){
            KartaMuddat kartaMuddat = new KartaMuddat();
            kartaMuddat.setKartaMuddat(kartaMuddatDto.getKartaMuddat());
            Optional<Bank> byId = bankRepository.findById(bankId);
            if (byId.isPresent()){
                kartaMuddat.setBank(byId.get());
                Optional<KartaTuri> byId1 = kartaTuriRepository.findById(turId);
                if (byId1.isPresent()){
                    kartaMuddat.setKartaTuri(byId1.get());
                    kartaMuddatRepository.save(kartaMuddat);
                    return new ApiRespons("Ma'lumotlar muvaffaqiyatli saqlandi",true);
                }
                return new ApiRespons("Bunday karta turi mavjud emas",false);
            }
            return new ApiRespons("Bunday bank mavjud emas",false);
        }
        return new ApiRespons("Bunday muddat allaqachon qo'shilgan",false);
    }

    @Override
    public ApiRespons addnotuser(Dto dto, Integer kartaMuddatID) {
        if (dto.getMijozStatus().equals(MijozStatus.ADD) && !usersRepository.existsByPasportRaqam(dto.getUsers().getPasportRaqam())){
            Manzil manzil = new Manzil(
                    dto.getManzil().getViloyat(),
                    dto.getManzil().getTuman(),
                    dto.getManzil().getKucha()
            );
            manzilRepository.save(manzil);
            Users users = new Users(
                    dto.getUsers().getFish(),
                    dto.getUsers().getEmail(),
                    dto.getUsers().getPasportRaqam(),
                    manzil
            );
            usersRepository.save(users);
            Karta karta = new Karta();
            Optional<KartaMuddat> byId = kartaMuddatRepository.findById(kartaMuddatID);
            String bankid = "";
            int bankid1 = byId.get().getBank().getId();
            if ( bankid1 < 10){
                bankid = ("0" + bankid1);
            }else if (bankid1 >= 10 && bankid1 < 100){
                bankid = String.valueOf(bankid1);
            }
            int kod1 = new Random().nextInt(99999);
            int kod2 = new Random().nextInt(99999);

            String kod = byId.get().getKartaTuri().getTurKodi() + bankid + String.valueOf(kod1) + String.valueOf(kod2);

            karta.setKartaRaqam(kod);
            karta.setUsers(users);
            karta.setBalance(0.0);
            karta.setPinKod(dto.getPinKod());
            karta.setAmalMuddat(LocalDate.now().plusYears(byId.get().getKartaMuddat()));
            Optional<Bank> byId1 = bankRepository.findById(byId.get().getBank().getId());
            karta.setBank(byId1.get());
            Optional<KartaTuri> byId2 = kartaTuriRepository.findById(byId.get().getKartaTuri().getId());
            karta.setKartaTuri(byId2.get());
            kartaRepository.save(karta);
            return new ApiRespons("Ma'lumot muvaffaqiyatli saqlandi",true);
        }
        if (dto.getMijozStatus().equals(MijozStatus.EDIT) && usersRepository.existsByPasportRaqam(dto.getUsers().getPasportRaqam())){
            Optional<Users> byPasportRaqam = usersRepository.findByPasportRaqam(dto.getUsers().getPasportRaqam());
            Users users = byPasportRaqam.get();

            Karta karta = new Karta();
            Optional<KartaMuddat> byId = kartaMuddatRepository.findById(kartaMuddatID);
            String bankid = "";
            int bankid1 = byId.get().getBank().getId();
            if ( bankid1 < 10){
                bankid = ("0" + bankid1);
            }else if (bankid1 >= 10 && bankid1 < 100){
                bankid = String.valueOf(bankid1);
            }
            int kod1 = new Random().nextInt(99999);
            int kod2 = new Random().nextInt(99999);

            String kod = byId.get().getKartaTuri().getTurKodi() + bankid + String.valueOf(kod1) + String.valueOf(kod2);

            karta.setKartaRaqam(kod);
            karta.setUsers(users);
            karta.setBalance(0.0);
            karta.setPinKod(dto.getPinKod());
            karta.setAmalMuddat(LocalDate.now().plusYears(byId.get().getKartaMuddat()));
            Optional<Bank> byId1 = bankRepository.findById(byId.get().getBank().getId());
            karta.setBank(byId1.get());
            Optional<KartaTuri> byId2 = kartaTuriRepository.findById(byId.get().getKartaTuri().getId());
            karta.setKartaTuri(byId2.get());
            kartaRepository.save(karta);
            return new ApiRespons("Ma'lumot muvaffaqiyatli saqlandi",true);
        }
        return null;
    }

    @Override
    public ApiRespons login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if (authenticate.isAuthenticated()){
            Optional<Xodim> byUsernameAndEmailCode = xodimRepository.findByUsernameAndEmailcode(loginDto.getUsername(),null);
            if (byUsernameAndEmailCode.isPresent()){
                Xodim principal = (Xodim) authenticate.getPrincipal();
                return new ApiRespons("Profilga xush kelibsiz" + "\t" + tokenGenerator.TokenOlish(principal.getUsername(),principal.getRole()),true);
            }
            return new ApiRespons("Akkountingiz faollashtirilmagan",false);
        }
        return new ApiRespons("Login yoki parol xato",false);
    }

    public boolean XabarYuborish(String email,String emailCode){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setFrom("Asqar");
            simpleMailMessage.setSubject("Tasdiqlash linki:");
            simpleMailMessage.setText("<p>Siz ishgaqabul qilindingiz. Quyidagi link orqali saytdan ro'yxatdan o'ting</p><br>" +
                    "<a href='http://localhost:8080/xodim/tasdiqlash?email="+email+"&emailCode="+emailCode+"'>EMAIL_TASDIQLASH</a>");
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }
    @Override
    public ApiRespons register(XodimDto xodimDto) {
        Optional<Xodim> byUsername = xodimRepository.findByUsername(xodimDto.getUsername());
        if (!byUsername.isPresent()){
            if (xodimDto.getPassword().equals(xodimDto.getPassword())){
                Xodim xodim = new Xodim();
                xodim.setFish(xodimDto.getFish());
                xodim.setUsername(xodimDto.getUsername());
                xodim.setPassword(passwordEncoder.encode(xodimDto.getPassword()));
                xodim.setRole(roleRepository.findByNomi(RoleConstanta.XODIM).get());
                String code = UUID.randomUUID().toString().substring(0,6);
                xodim.setEmailcode(code);
                if (XabarYuborish(xodim.getUsername(),code)){
                    xodimRepository.save(xodim);
                    return new ApiRespons("Xodim ro'yxatdan muvaffaqiyatli o'tkazildi. Xisobni faollashtirish " +
                            "uchun email pochtaga xabar yuborildi",true);
                }
                return new ApiRespons("Ro'yxatdan o'tolmadi. Emailda xato bor",false);
            }
            return new ApiRespons("Parollar mos emas",false);
        }
        return new ApiRespons("Bunday username ro'yxatdan o'tgan",false);
    }

    @Override
    public ApiRespons faollashtirish(String email, String emailCode) {
        System.out.println(email + " " + emailCode);
        Optional<Xodim> byUsernameAndEmailCode = xodimRepository.findByUsernameAndEmailcode(email, emailCode);
        if (byUsernameAndEmailCode.isPresent()){
            Xodim xodim = byUsernameAndEmailCode.get();
            xodim.setEnabled(true);
            xodim.setEmailcode(null);
            xodimRepository.save(xodim);
            return new ApiRespons("Hisobingiz faollashtirildi",true);
        }
        return new ApiRespons("Hisob allaqachon faollashtirilgan",false);
    }

    @Override
    public ApiRespons addkomissiya(Integer bank, Integer kartaTuri, Komissiya komissiya) {
        Optional<Bank> byId = bankRepository.findById(bank);
        Optional<KartaTuri> byId1 = kartaTuriRepository.findById(kartaTuri);
        boolean b = komissiyaRepository.existsByKartaTuriIdAndBankId(kartaTuri, bank);
        if (!b){
            if (byId.isPresent()){
                if (byId1.isPresent()){
                    Komissiya komissiya1 = new Komissiya(
                            komissiya.getFoiz(),
                            byId1.get(),
                            byId.get()
                    );
                    komissiyaRepository.save(komissiya1);
                    return new ApiRespons("Komissiya muvaffaqiyatli saqlandi",true);
                }
                return new ApiRespons("Bunday IDli karta turi mavjud emas",false);
            }
            return new ApiRespons("Bunday IDli bank mavjud emas",false);
        }
        return new ApiRespons("Bankning ushbu kartasiga komissiya allaqachon kiritilgan",false);
    }

    @Override
    public ApiRespons addbank(BankDto bankDto) {
        boolean b = bankRepository.existsByNomi(bankDto.getNomi());
        boolean b1 = manzilRepository.existsByViloyatAndTumanAndKucha(bankDto.getViloyat(), bankDto.getTuman(), bankDto.getKucha());
        if (!b1 && !b){
            Manzil manzil = new Manzil();
            manzil.setViloyat(bankDto.getViloyat());
            manzil.setTuman(bankDto.getTuman());
            manzil.setKucha(bankDto.getKucha());
            manzilRepository.save(manzil);
            Bank bank = new Bank(
                    bankDto.getNomi(),
                    manzil
            );
            bankRepository.save(bank);
            return new ApiRespons("Bank ma'lumotlari muvaffaqiyatli saqlandi!",true);
        }
        return new ApiRespons("Kechirasiz bunday manzil bo'sh emas yoki bunday nomli bank mavjud",false);
    }

    @Override
    public ApiRespons addbankomat(BankomatDto bankomatDto, Integer bankId, Integer xodimId) {
        Optional<Bank> byId1 = bankRepository.findById(bankId);
        Optional<Xodim> byId = xodimRepository.findById(xodimId);
        if (byId1.isPresent()){
            if (byId.isPresent()){
                Manzil manzil = new Manzil(
                        bankomatDto.getManzil().getViloyat(),
                        bankomatDto.getManzil().getTuman(),
                        bankomatDto.getManzil().getKucha()
                );
                manzilRepository.save(manzil);
                Kupyura kupyura = new Kupyura(
                        bankomatDto.getKupyura().getTarif(),
                        bankomatDto.getBesh(),
                        bankomatDto.getUn(),
                        bankomatDto.getYigirma(),
                        bankomatDto.getEllik(),
                        bankomatDto.getYuz(),
                        bankomatDto.getIkkiyuz()
                );
                kupyuraRepository.save(kupyura);
                Bankomat bankomat = new Bankomat(
                        bankomatDto.getPulSigim(),
                        bankomatDto.getBalance(),
                        bankomatDto.getMaxKirimChiqim(),
                        bankomatDto.getMinKirimChiqim(),
                        bankomatDto.getChegara(),
                        bankomatDto.getIkkiyuz(),
                        bankomatDto.getYuz(),
                        bankomatDto.getEllik(),
                        bankomatDto.getYigirma(),
                        bankomatDto.getUn(),
                        bankomatDto.getBesh(),
                        byId1.get(),
                        manzil,
                        byId.get(),
                        kupyura
                );
                Bankomat save = bankomatRepository.save(bankomat);
                for (Integer s : bankomatDto.getKartaTuriList()) {
                    Optional<KartaTuri> byId2 = kartaTuriRepository.findById(s);
                    if (byId2.isPresent()){
                        BankomatKartaTuri bankomatKartaTuri=new BankomatKartaTuri();
                        bankomatKartaTuri.setBankomat(save);
                        bankomatKartaTuri.setKartaTuri(byId2.get());
                        bankomatKartaTuriRepository.save(bankomatKartaTuri);
                    }
                }
                return new ApiRespons("Ma'lumot saqlandi",true);
            }
            return new ApiRespons("Bunday xodim mavjud emas",false);
        }
        return new ApiRespons("Bunday bank mavjud emas",false);
    }

    public boolean KartaXabar(String email, Double miqdor, boolean holat){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setFrom("Asqar");
            simpleMailMessage.setSubject("Bildirishnoma:");
            if (holat == true){
                simpleMailMessage.setText(" Karta hisobi " + miqdor + " ga to'ldirildi.");
            }else {
                simpleMailMessage.setText(" Karta hisobi " + miqdor + " ga kamaydi");
            }
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }
    @Override
    public ApiRespons takemoney(MoneyConvertDto moneyConvertDto, String kartaRaqam, Integer bankomatId, Integer pinKod) {
        Optional<Karta> byKartaRaqam = kartaRepository.findByKartaRaqam(kartaRaqam);
        if (byKartaRaqam.isPresent()){
            Optional<Bankomat> byId = bankomatRepository.findById(bankomatId);
            Optional<BankomatKartaTuri> byBankomatIdAndKartaTuriId = bankomatKartaTuriRepository.findByBankomatIdAndKartaTuriId(bankomatId, byKartaRaqam.get().getKartaTuri().getId());
            Optional<Komissiya> byBank = komissiyaRepository.findByKartaTuriAndBank(byKartaRaqam.get().getKartaTuri(),byId.get().getBank());
            if (byBankomatIdAndKartaTuriId.isPresent()){
                if (byKartaRaqam.get().getPinKod().equals(pinKod)){
                    LocalDate localDate = LocalDate.now();
                    LocalDate amalMuddati = byKartaRaqam.get().getAmalMuddat();
                    if ((amalMuddati.getYear() == localDate.getYear() && amalMuddati.getMonthValue() < localDate.getMonthValue()) || (amalMuddati.getYear() > localDate.getYear())){
                        double miqdor = moneyConvertDto.getSumma();
                        double balance = byId.get().getBalance();
                        double foiz = byBank.get().getFoiz();
                        if (byKartaRaqam.get().getBank().getId().equals(byId.get().getBank().getId())){
                            if (miqdor < byKartaRaqam.get().getBalance()){
                                if (balance > miqdor){
                                    if (miqdor <= byId.get().getMaxKirimChiqim() && miqdor >= byId.get().getMinKirimChiqim() && miqdor % 5000 == 0){
                                        Karta karta = byKartaRaqam.get();
                                        karta.setBalance(byKartaRaqam.get().getBalance()-miqdor);
                                        kartaRepository.save(karta);
                                        if (byId.get().getBalance() <= byId.get().getChegara()){
                                            XodimXabar(byId.get().getXodim().getUsername(),bankomatId,byId.get().getBalance());
                                        }
                                        double miqdor1 = miqdor;
                                        Integer ikkiyuz = 0;
                                        Integer yuz = 0;
                                        Integer ellik = 0;
                                        Integer yigirma = 0;
                                        Integer un = 0;
                                        Integer besh = 0;
                                        while ((byId.get().getIkkiyuz() > 0) && miqdor1 >= 200000){
                                            ikkiyuz ++;
                                            miqdor1 -= 200000;
                                        }
                                        while ((byId.get().getYuz() > 0) && miqdor1 >= 100000){
                                            yuz ++;
                                            miqdor1 -= 100000;
                                        }
                                        while ((byId.get().getEllik() > 0) && miqdor1 >= 50000){
                                            ellik ++;
                                            miqdor1 -= 50000;
                                        }
                                        while ((byId.get().getYigirma() > 0) && miqdor1 >= 20000){
                                            yigirma ++;
                                            miqdor1 -= 20000;
                                        }
                                        while ((byId.get().getUn() > 0) && miqdor1 >= 10000){
                                            un ++;
                                            miqdor1 -= 10000;
                                        }
                                        while ((byId.get().getBesh() > 0) && miqdor1 >= 5000){
                                            besh ++;
                                            miqdor1 -= 5000;
                                        }
                                        Bankomat bankomat = byId.get();
                                        bankomat.setBalance(balance - miqdor);
                                        bankomat.setIkkiyuz(byId.get().getIkkiyuz() - ikkiyuz);
                                        bankomat.setYuz(byId.get().getYuz() - yuz);
                                        bankomat.setEllik(byId.get().getEllik() - ellik);
                                        bankomat.setYigirma(byId.get().getYigirma() - yigirma);
                                        bankomat.setUn(byId.get().getUn() - un);
                                        bankomat.setBesh(byId.get().getBesh() - besh);
                                        bankomatRepository.save(bankomat);
                                        KartaXabar(byKartaRaqam.get().getUsers().getEmail(),miqdor,false);
                                        return new ApiRespons("Muvaffaqiyatli pul yechildi",true);
                                    }
                                    return new ApiRespons("Kiritilgan miqdor xizmat ko'rsatish miqdoridan ko'p yoki kam yoki besh mingga karrali emas",false);
                                }
                                return new ApiRespons("Bankomatda yetarli mabalg' yo'q",false);
                            }
                            return new ApiRespons("Kartada yetarli mablag' mavjud emas",false);
                        }
                        else {
                            if (miqdor + miqdor * foiz < byKartaRaqam.get().getBalance()){
                                if (balance > miqdor){
                                    if (miqdor <= byId.get().getMaxKirimChiqim() && miqdor >= byId.get().getMinKirimChiqim() && miqdor % 5000 == 0){
                                        Karta karta = byKartaRaqam.get();
                                        karta.setBalance(byKartaRaqam.get().getBalance()-(miqdor + miqdor * foiz));
                                        kartaRepository.save(karta);
                                        if (byId.get().getBalance() <= byId.get().getChegara()){
                                            XodimXabar(byId.get().getXodim().getUsername(),bankomatId,byId.get().getBalance());
                                        }
                                        double miqdor1 = miqdor;
                                        Integer ikkiyuz = 0;
                                        Integer yuz = 0;
                                        Integer ellik = 0;
                                        Integer yigirma = 0;
                                        Integer un = 0;
                                        Integer besh = 0;
                                        while ((byId.get().getIkkiyuz() > 0) && miqdor1 >= 200000){
                                            ikkiyuz ++;
                                            miqdor1 -= 200000;
                                        }
                                        while ((byId.get().getYuz() > 0) && miqdor1 >= 100000){
                                            yuz ++;
                                            miqdor1 -= 100000;
                                        }
                                        while ((byId.get().getEllik() > 0) && miqdor1 >= 50000){
                                            ellik ++;
                                            miqdor1 -= 50000;
                                        }
                                        while ((byId.get().getYigirma() > 0) && miqdor1 >= 20000){
                                            yigirma ++;
                                            miqdor1 -= 20000;
                                        }
                                        while ((byId.get().getUn() > 0) && miqdor1 >= 10000){
                                            un ++;
                                            miqdor1 -= 10000;
                                        }
                                        while ((byId.get().getBesh() > 0) && miqdor1 >= 5000){
                                            besh ++;
                                            miqdor1 -= 5000;
                                        }
                                        Bankomat bankomat = byId.get();
                                        bankomat.setBalance(balance - miqdor);
                                        bankomat.setIkkiyuz(byId.get().getIkkiyuz() - ikkiyuz);
                                        bankomat.setYuz(byId.get().getYuz() - yuz);
                                        bankomat.setEllik(byId.get().getEllik() - ellik);
                                        bankomat.setYigirma(byId.get().getYigirma() - yigirma);
                                        bankomat.setUn(byId.get().getUn() - un);
                                        bankomat.setBesh(byId.get().getBesh() - besh);
                                        bankomatRepository.save(bankomat);
                                        KartaXabar(byKartaRaqam.get().getUsers().getEmail(),miqdor,false);
                                        return new ApiRespons("Muvaffaqiyatli pul yechildi",true);
                                    }
                                    return new ApiRespons("Kiritilgan miqdor xizmat ko'rsatish miqdoridan ko'p yoki kam yoki besh mingga karrali emas",false);
                                }
                                return new ApiRespons("Bankomatda yetarli mabalg' yo'q",false);
                            }
                            return new ApiRespons("Kartada yetarli mablag' mavjud emas",false);
                        }
                    }
                    return new ApiRespons("Kartaning amal muddati tugagan",false);
                }
                return new ApiRespons("Kiritilgan pinKod noto'g'ri",false);
            }
            return new ApiRespons("Bankomat ushbu turdagi kartaga xizmat ko'rsatmaydi",false);
        }
        return new ApiRespons("Bunday karta mavjud emas",false);
    }

    public boolean XodimXabar(String email, Integer bankomatId, Double miqdor){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setFrom("Asqar");
            simpleMailMessage.setSubject("Bildirishnoma:");
            simpleMailMessage.setText(bankomatId + " IDli bankomat balansi " + miqdor + " dan kamaydi.");
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }



    @Override
    public ApiRespons addmoney(Integer bankomatId, String kartaRaqam, double summa, AddMoneyDto addMoneyDto) {
        Optional<Bankomat> byId = bankomatRepository.findById(bankomatId);
        Optional<Karta> byKartaRaqam = kartaRepository.findByKartaRaqam(kartaRaqam);
        Optional<Komissiya> byBank = komissiyaRepository.findByKartaTuriAndBank(byKartaRaqam.get().getKartaTuri(),byId.get().getBank());
        if (byId.isPresent()){
            if (byKartaRaqam.isPresent()){
                double foiz = byBank.get().getFoiz();
                double mablag = byKartaRaqam.get().getBalance();
                if (summa < byId.get().getMaxKirimChiqim() && summa > byId.get().getMinKirimChiqim()){

                    int ikkiyuz = addMoneyDto.getIkkiyuz();
                    int yuz = addMoneyDto.getYuz();
                    int ellik = addMoneyDto.getEllik();
                    int yigirma = addMoneyDto.getYigirma();
                    int un = addMoneyDto.getUn();
                    int besh = addMoneyDto.getBesh();

                    double jami = 200000 * ikkiyuz + 100000 * yuz + 50000 * ellik + 20000 * yigirma + 10000 * un + 5000 * besh;
                    if (jami == summa){
                        Karta karta = byKartaRaqam.get();
                        karta.setBalance(mablag + summa - summa * foiz);
                        kartaRepository.save(karta);
                        KartaXabar(byKartaRaqam.get().getUsers().getEmail(),summa,true);
                        return new ApiRespons(byKartaRaqam.get().getKartaRaqam() + " karta hisobi to'ldirildi",true);
                    }
                    return new ApiRespons("Kiritilga pul ko'rsatilgan miqdorga teng emas",false);
                }
                return new ApiRespons("Siz kiritgan qiymatdagi summaga xizmat ko'rsatilmaydi!",false);
            }
            return new ApiRespons("Bunday karta mavjud emas",false);
        }
        return new ApiRespons("Bunday bankomat mavjud emas",false);
    }

    @Override
    public ApiRespons editbankomatmoney(Integer bankomatId, AddMoneyDto addMoneyDto) {
        Optional<Bankomat> byId = bankomatRepository.findById(bankomatId);
        if (byId.isPresent()){
            Integer ikkiyuz = 0;
            Integer yuz = 0;
            Integer ellik = 0;
            Integer yigirma = 0;
            Integer un = 0;
            Integer besh = 0;
        }
        return new ApiRespons("Bunday bankomat mavjud emas",false);
    }
}
