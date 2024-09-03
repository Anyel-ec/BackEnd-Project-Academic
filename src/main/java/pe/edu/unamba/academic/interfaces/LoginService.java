package pe.edu.unamba.academic.interfaces;

import pe.edu.unamba.academic.models.messages.request.LoginUser;
import pe.edu.unamba.academic.models.messages.response.JsonResponse;

public interface LoginService {

    JsonResponse logear(LoginUser login);

}
