					{% if newbieNextGift < 3 %}
					<div class="gift-img">
					  {% if newbieNextGift == 1 %}
                          <img src="{{ site.staticContentPath }}/web-gallery/v2/images/welcome/newbie_furni/noob_stool_{{ newbieRoomLayout }}.png" alt="{{ locale.habblet_nextgift_my_first_starter_stool }}">
					  {% elseif newbieNextGift == 2 %}
						  <img src="{{ site.staticContentPath }}/web-gallery/v2/images/welcome/newbie_furni/noob_plant.png" alt="{{ locale.habblet_nextgift_my_first_plant }}">
					  {% endif %}
                    </div>
                    <div class="gift-content-container">
                      <p class="gift-content">
					  {% if newbieNextGift == 1 %}
                        {{ locale.habblet_nextgift_your_next_piece_of_free_furniture_will_be }} <strong>{{ locale.habblet_nextgift_starter_stool }}</strong>
					  {% elseif newbieNextGift == 2 %}
						{{ locale.habblet_nextgift_your_next_piece_of_free_furniture_will_be }} <strong>{{ locale.habblet_nextgift_lucky_bamboo_plant }}</strong>
					  {% endif %}
					  </p>
                      <p>
                        <b>{{ locale.habblet_nextgift_time_left }}</b>
                        <span id="gift-countdown"></span>
                      </p>
                      <p class="last">
                        <a class="new-button green-button" href="{{ site.sitePath }}/client?forwardId=2&roomId={{ playerDetails.getSelectedRoomId() }}" target="client" onclick="HabboClient.roomForward(this, '{{ playerDetails.getSelectedRoomId() }}', 'private'); return false;"><b>{{ locale.habblet_nextgift_go_to_your_room_gt_gt }}</b><i></i></a>
                      </p>
                      <br style="clear: both" />
                    </div>
                    <script type="text/javascript">
                      L10N.put('time.hours', '{0}h');
                      L10N.put('time.minutes', '{{ locale.habblet_nextgift_zero_min|escape('js') }}');
                      L10N.put('time.seconds', '{0}s');
                      GiftQueueHabblet.init({{ newbieGiftSeconds }});
                    </script>
					{% else %}
					<p>
                      {{ locale.habblet_nextgift_how_do_you_get_more_furniture_into_your_room }}
                    </p>
                    <p>
                      {{ locale.habblet_nextgift_you_could_buy_a_set_of_furniture_for_just_three_credits_including_a_lamp_mat_and_two_armchairs_how_do_you_do_that }}
                    </p>
                    <ul>
                      <li>{{ locale.habblet_nextgift_one_buy_some_credits_from_the }} <a href="{{ site.sitePath }}/credits">{{ locale.habblet_nextgift_credits }}</a> {{ locale.habblet_nextgift_section }}</li>
                      <li>{{ locale.habblet_nextgift_two_open_the_catalogue_from_the_hotel_toolbar_chair_icon }}</li>
                      <li>{{ locale.habblet_nextgift_three_open_the_deals_section }}</li>
                      <li>{{ locale.habblet_nextgift_four_pick_up_the_furni_set_you_want }}</li>
                      <li>{{ locale.habblet_nextgift_five_thank_you_for_shopping }}</li>
                    </ul>
                    <p class="aftergift-img">
                      <img src="{{ site.staticContentPath }}/web-gallery/v2/images/giftqueue/aftergifts.png" alt="" width="289" height="63" />
                    </p>
                    <p class="last">
                      <a class="new-button green-button" href="{{ site.sitePath }}/client?forwardId=2&roomId={{ playerDetails.getSelectedRoomId() }}" target="client" onclick="HabboClient.roomForward(this, '{{ playerDetails.getSelectedRoomId() }}', 'private'); return false;"><b>{{ locale.habblet_nextgift_go_to_your_room_gt_gt }}</b><i></i></a>
                    </p>
                    <script type="text/javascript">					  
                      HabboView.add(GiftQueueHabblet.initClosableHabblet);
                    </script>
					{% endif %}